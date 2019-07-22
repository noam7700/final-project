package execution.genericSqlQueriesExecution;

import java.sql.SQLException;
import java.util.List;

/**
 * @author amit
 *
 */
public interface IGenericExecuteSqlQueries {
	 void insertToTable(String tableName, List<Object> objects) throws SQLException;

	 int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException;

	 void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException;

	 void deleteValuesFromTable(String tableName, List<String> objectNames, List<Object> objects) throws SQLException;

	 List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
	           throws SQLException;

	 List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object,
	           String columnsToSelect) throws SQLException;

	 List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames, List<Object> objectsValues,
	           String columnsToSelect) throws SQLException;

	 void updateTableColumn(String tableName, String columnToUpdate, Object valueToUpdate, String columnCondition,
	           Object conditonValue) throws SQLException;

	 List<List<Object>> selectAllColumns(String tableName, String columnsToSelect) throws SQLException;

	 void updateTableColumns(String tableName, String columnToUpdate, Object valueToUpdate, String columnCondition,
	           Object conditonValue) throws SQLException;

	 List<List<Object>> selectTableContent(String tableName) throws SQLException;

	 List<List<Object>> selectColumnsByPrefixValue(String tableName, String objectName, Object object,
	           String columnsToSelect) throws SQLException;

}
