package gui.tableview

import javafx.collections.ObservableList
import javafx.scene.control.TableColumn
import javafx.scene.control.TablePosition
import javafx.scene.control.TableView
import tornadofx.observableList

/**
 * Ovakav TableViewSelectionModel se koristi kada se želi da se onemogući biranje vrsta u tabeli.
 */
class  NullSelectionModel<S>(tableView: TableView<S>) : TableView.TableViewSelectionModel<S>(tableView) {
    override fun selectRightCell() {

    }

    override fun clearSelection(row: Int, column: TableColumn<S, *>?) {

    }

    override fun isSelected(row: Int, column: TableColumn<S, *>?): Boolean {
        return false
    }

    override fun selectAboveCell() {

    }

    override fun getSelectedCells(): ObservableList<TablePosition<Any, Any>>? {
        return observableList()
    }

    override fun selectLeftCell() {

    }

    override fun select(row: Int, column: TableColumn<S, *>?) {

    }

    override fun selectBelowCell() {

    }

    override fun clearAndSelect(row: Int, column: TableColumn<S, *>?) {

    }
}