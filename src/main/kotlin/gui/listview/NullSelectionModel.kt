package gui.listview

import javafx.collections.ObservableList
import javafx.scene.control.MultipleSelectionModel
import tornadofx.observableList


class NullSelectionModel<T> : MultipleSelectionModel<T>() {
    override fun clearSelection(index: Int) {
        
    }

    override fun clearSelection() {
        
    }

    override fun selectLast() {
        
    }

    override fun isSelected(index: Int): Boolean {
        return false
    }

    override fun getSelectedIndices(): ObservableList<Int> {
        return observableList()
    }

    override fun selectAll() {
        
    }

    override fun getSelectedItems(): ObservableList<T> {
        return observableList()
    }

    override fun select(index: Int) {
        
    }

    override fun select(obj: T) {
        
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun selectNext() {
        
    }

    override fun selectPrevious() {
        
    }

    override fun selectIndices(index: Int, vararg indices: Int) {
        
    }

    override fun selectFirst() {
        
    }

    override fun clearAndSelect(index: Int) {
        
    }
}