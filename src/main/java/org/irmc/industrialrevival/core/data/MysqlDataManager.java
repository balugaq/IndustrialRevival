package org.irmc.industrialrevival.core.data;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.irmc.industrialrevival.core.IndustrialRevival;
import org.irmc.industrialrevival.core.data.mapper.GuideSettingsMapper;
import org.irmc.industrialrevival.core.data.mapper.ResearchStatusMapper;
import org.irmc.industrialrevival.core.guide.GuideSettings;
import org.jetbrains.annotations.NotNull;

public class MysqlDataManager implements IDataManager {
    private SqlSession session;

    public MysqlDataManager() throws SQLException {
        FileConfiguration config = IndustrialRevival.getInstance().getConfig();
        String host = config.getString("storage.mysql.host");
        int port = config.getInt("storage.mysql.port");
        String url = host + ":" + port;
        String username = config.getString("storage.mysql.username");
        String password = config.getString("storage.mysql.password");
        connect(url, username, password);
    }

    @Override
    public void connect(String url, String username, String password) throws SQLException {
        DataSource dataSource = new UnpooledDataSource("com.mysql.cj.jdbc.Driver", getUrl(url), username, password);
        dataSource.setLoginTimeout(5000);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("default", transactionFactory, dataSource);
        Configuration configuration = newMybatisConfiguration(environment);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        session = sqlSessionFactory.openSession(true);

        createTables();
    }

    @Override
    public void close() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public GuideSettings getGuideSettings(@NotNull String playerName) {
        return session.getMapper(GuideSettingsMapper.class).get(playerName);
    }

    @Override
    public void saveGuideSettings(@NotNull String playerName, @NotNull GuideSettings settings) {
        session.getMapper(GuideSettingsMapper.class).save(playerName, settings);
    }

    @Override
    public @NotNull YamlConfiguration getResearchStatus(String playerName) {
        String yml = session.getMapper(ResearchStatusMapper.class).getResearchStatus(playerName);
        if (yml == null) {
            return new YamlConfiguration();
        } else {
            yml = new String(Base64.getDecoder().decode(yml));
            return YamlConfiguration.loadConfiguration(new StringReader(yml));
        }
    }

    @Override
    public void saveResearchStatus(String playerName, YamlConfiguration researchStatus) {
        String str = researchStatus.saveToString();
        String b64 = Base64.getEncoder().encodeToString(str.getBytes());
        session.getMapper(ResearchStatusMapper.class).insertResearchStatus(playerName, b64);
    }

    private String getUrl(String url) {
        return "jdbc:mysql://" + url + "/industrialrevival";
    }

    private void createTables() throws SQLException {
        try (Connection conn = session.getConnection()) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS guide_settings (" + "    username TEXT NOT NULL,"
                            + "    fireWorksEnabled BOOLEAN NOT NULL,"
                            + "    learningAnimationEnabled BOOLEAN NOT NULL,"
                            + "    language TEXT NOT NULL);")
                    .execute();

            conn.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS research_status (username TEXT NOT NULL, researchStatusJson TEXT NOT NULL)")
                    .execute();
        }
    }
}
