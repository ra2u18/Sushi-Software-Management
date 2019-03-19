package comp1206.sushi.server;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class TableManager {

    /**
     * CREATE THE COLUMNNAMEGENERATOR IN THE CONTROLLER CLASS AS A THREAD!!
     * */


    public TableManager(TableView<Object>tableView, List<String> columnNames){
        setColumnNames(tableView, columnNames);
    }

    private void setColumnNames(TableView<Object> tableView, List<String> columnNames){

        List<TableColumn<Object, String>> listColumns = new ArrayList<>();

        for ( String columnTitle : columnNames ){
            TableColumn<Object, String> column = new TableColumn<>(columnTitle);
            listColumns.add(column);

            StringBuilder sb = new StringBuilder();
            String[] argumentInference = columnTitle.split(" ");
            if( argumentInference.length > 1 ) {
                argumentInference[0].toLowerCase();
                sb.append(argumentInference[0]);
                sb.append(argumentInference[1]);
                column.setCellValueFactory(new PropertyValueFactory<>(sb.toString()));
            }
            else column.setCellValueFactory(new PropertyValueFactory<>(columnTitle));
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columnNames.size()));
        }
        tableView.getColumns().setAll(listColumns);

    }

    public void setTable(TableView<Object> tableView, ObservableList<?> observableList){

        //System.out.println(observableList);
        tableView.setItems((ObservableList<Object>) observableList);

    }
}

