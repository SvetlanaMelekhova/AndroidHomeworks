package by.htp.first.homework2_1.`interface`

interface Notifier {
    fun addObserver(obs: Observer?)
    fun removeObserver(obs: Observer?)
    fun notifyObserver()
}