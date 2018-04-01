package ru.otus.hibernate.service;

/**
 * Created by abyakimenko on 01.04.2018.
 */
/*public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private final Connection connection;
    private final UserDataSetDao userDao;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
        userDao = new UserDataSetDao(connection);
    }

    @Override
    public void save(UserDataSet user) {
        userDao.save(user);
    }

    @Override
    public UserDataSet load(long id) {
        return userDao.load(id);
    }

    @Override
    public void deleteTables() {
        userDao.deleteTable();
    }

    @Override
    public void createTables() {
        userDao.createTable();
    }

    @Override
    public List<String> getAllTables() {
        List<String> tablesList = new ArrayList<>();
        String[] types = {"TABLE"};

        try {
            ResultSet resultSet = connection.getMetaData().getTables("jdbc", null, "%", types);
            while (resultSet.next()) {
                tablesList.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Can't get meta info all tables", e);
        }
        return tablesList;
    }
}*/
