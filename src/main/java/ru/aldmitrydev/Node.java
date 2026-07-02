package ru.aldmitrydev;

public class Node<K, E> {

    // Элемент-значение
    private E element;
    // Ключ
    private final K key;
    // Ссылка на следующий узел
    private Node<K, E> nextNode;


    protected Node(K key, E element) {
        this.element = element;
        this.key = key;
    }

    public void setNextNode(Node<K, E> nextNode) {
        this.nextNode = nextNode;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<K, E> getNextNode() {
        return nextNode;
    }

    public E getElement() {
        return element;
    }

    public K getKey() {
        return key;
    }
}