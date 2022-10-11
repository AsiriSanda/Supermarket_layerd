package Controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportsFormController {
    public TableView tblItem;
    public TableColumn colItemId;
    public TableColumn colDesc;
    public TableColumn colPackSize;
    public TableColumn colMovable;
    public JFXDatePicker datePicker;
    public TextField mostMovable;
    public TextField leastMovable;

    public void datePickerOnAction(ActionEvent actionEvent) {
    }
}
