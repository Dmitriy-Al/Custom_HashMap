package ru.aldmitrydev;
import java.util.Arrays;
import java.util.Objects;

public class HashMap<K, E> {

    // Количество узлов, содержащихся в Node<K, E>[] nodes
    private int size;
    // Размер массива Node<K, E>[] nodes
    private int amount;
    // Элемент с ключом null
    private E nullKeyElement;
    // Массив ячеек-бакетов
    private Node<K, E>[] nodes;
    // Счетчик для элемента с ключом null
    private int nullKeyElementCount;

    /**
     * Одна из особенностей собственной реализации HashMap - элемент связанный с ключом null
     * хранится не в виде узла в массиве Node<K, E>[] nodes, а как отдельный элемент.
     */

    public HashMap() {
        this.amount = 16;
        //noinspection unchecked - так же raw type подавлен в оригинальной мапе
        nodes = (Node<K, E>[]) new Node[amount];
    }

    /* Метод для добавления пар ключ-значение. Возвращает замененный элемент E в случае, когда
    добавляемый ключ совпадает с уже имеющимся, в остальных случаях вернет null */
    public E put(K key, E element) {
        // Добавление элемента, если ключ == null
        if (key == null) {
            if (nullKeyElementCount == 1) {
                E e = nullKeyElement;
                nullKeyElement = element;
                return e;
            }
            nullKeyElement = element;
            nullKeyElementCount = 1;
            return null;
        }

        int keyHashCode = key.hashCode();
        int index = findIndex(keyHashCode);

        // Добавление узла в пустой бакет
        if (nodes[index] == null) {
            nodes[index] = new Node<>(key, element);
            size++;
            enlargeNodes();
        } else {
            Node<K, E> node = nodes[index];
            while (true) {
                // Если ключи совпадают, происходит замена элемента на новый, старый элемент возвращается методом
                if (node.getKey().equals(key)) {
                    E e = node.getElement();
                    node.setElement(element);
                    return e;
                }
                // Добавление узла в цепочку
                if (node.getNextNode() == null) {
                    node.setNextNode(new Node<>(key, element));
                    size++;
                    enlargeNodes();
                    return null;
                }
                node = node.getNextNode();
            }
        }
        return null;
    }

    // Метод для увеличения размера массива Node<K, E>[] nodes
    private void enlargeNodes() {
        if (amount >= 1073741824) return; // Увеличение массива Node<K, E>[] nodes ограничено
        long emptyNodes = Arrays.stream(nodes).filter(Objects::isNull).count();
        if (size > amount * 0.75) {
            size = 0;
            Node<K, E>[] tempNodes = nodes.clone();
            //noinspection unchecked
            nodes = (Node<K, E>[]) new Node[amount = amount * 2];

            // Рехеширование элементов
            for (Node<K, E> n : tempNodes) {
                while (n != null) {
                    this.put(n.getKey(), n.getElement());
                    n = n.getNextNode();
                }
            }
        }
    }

    /* Метод возвращает элемент-значение для переданного ключа key,
    либо null, если ключ отсутствует в Node<K, E>[] nodes */
    public E get(K key) {
        // Получение элемента c ключом null, если имеется
        if (key == null) return nullKeyElement;

        int keyHashCode = key.hashCode();
        int index = findIndex(keyHashCode);
        Node<K, E> node;
        // Получение элемента из первого узла при совпадении ключей
        if (nodes.length > index && nodes[index] != null) {
            node = nodes[index];
            if (node.getKey().equals(key)) {
                return node.getElement();
            }
        } else {
            return null;
        }
        // Получение элемента из следующих за первым узллов при совпадении ключей
        while (node.getNextNode() != null) {
            node = node.getNextNode();
            if (node.getKey().equals(key)) {
                return node.getElement();
            }
        }
        return null;
    }

    /* Метод remove удаляет узел, если в параметр передан соответствующий ему ключ key.
     В случае успешного удаления возвращает true, в противном случае false */
    public boolean remove(K key) {
        // Удаление элемента c ключом null, если имеется
        if (key == null) {
            if (nullKeyElementCount == 1) {
                nullKeyElement = null;
                nullKeyElementCount = 0;
                return true;
            }
            return false;
        }

        int keyHashCode = key.hashCode();
        int index = findIndex(keyHashCode);
        if (index > amount - 1 || nodes[index] == null) return false;

        // Удаление первого узла при совпадении ключей
        if (nodes[index].getKey().equals(key)) {
            nodes[index] = nodes[index].getNextNode();
            size--;
            return true;
        }

        // Удаление следущих за первым узлов при совпадении ключей
        Node<K, E> node = nodes[index];
        while (node.getNextNode() != null) {
            if (node.getNextNode().getKey().equals(key)) {
                node.setNextNode(node.getNextNode().getNextNode());
                size--;
                return true;
            } else {
                node = node.getNextNode();
            }
        }
        return false;
    }

    // Метод находит индекс-бакет в массиве Node<K, E>[] nodes для добавления нового узла
    private int findIndex(int hashCode) {
        int hash = hashCode ^ (hashCode >>> 16);
        return (amount - 1) & hash;
    }

    // Метод возвращает общее количество узлов содержащихся в Node<K, E>[] nodes + счетчик null-ключа
    public int size() {
        return size + nullKeyElementCount;
    }

    // С помощью метода printInfo() интересно наблюдать за распределением элементов и рядов в мапе =)
    public void printInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) continue;
            stringBuilder.append("\nrow ").append(i).append("> key =").append(nodes[i].getKey());
            Node<K, E> node = nodes[i];
            while (node.getNextNode() != null) {
                stringBuilder.append("> key =").append(node.getNextNode().getKey());
                node = node.getNextNode();
            }
        }
        if (nullKeyElementCount == 1) stringBuilder.append("\nrow x > key = null");
        System.out.println(stringBuilder);
    }

}
