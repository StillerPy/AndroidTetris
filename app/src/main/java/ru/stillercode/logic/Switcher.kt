package ru.stillercode.logic

class Switcher<T>(val list: List<T>) {
    var i = 0
    fun get(): T {
        return list[i]
    }
    fun getNext(): T {
        return list[(i + 1) % list.size]
    }
    fun switch() {
        i = (i + 1) % list.size
    }
}