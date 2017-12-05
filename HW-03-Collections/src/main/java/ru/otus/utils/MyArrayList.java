package ru.otus.utils;

import java.util.*;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 03 - ArrayList realisation
 */
public class MyArrayList<T> implements List<T>{

  private static final Object[] EMPTY_DATA = {};

  // текущий размер массива
  private int size;

  // Массив элементов
  private Object[] elements;

  // размер списка по умолчанию
  private static final int DEFAULT_CAPACITY = 10;

  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  private String getIndexOutOfBoundsMess(int index) {
    return ("Index: "+index+", Size: "+this.size);
  }

  // проверка допустимого значения индекса
  private void rangeCheck(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException(getIndexOutOfBoundsMess(index));
  }

  // проверка и при необходимости увеличение размера списка
  private void checkAndGrowCapacity(int newCount) {
    int newSize = Math.max(size+newCount, DEFAULT_CAPACITY);
    int currentLength = elements.length;

    if (newSize > currentLength) {
      int newCap = currentLength + (currentLength >> 1); // K ~ 1.5

      if (newCap < newSize) newCap = newSize;

      if (newCap < newCount) newCap = newCount;

      if (newCap > MAX_ARRAY_SIZE) newCap = MAX_ARRAY_SIZE;

      elements = Arrays.copyOf(elements, newCap);
    }
  }

  private class Iter implements Iterator<T> {
    int cursor;
    int lastRet = -1;

    public boolean hasNext() {
      return cursor != size;
    }

    public T next() {
      int i = cursor;
      if (i >= size)
        throw new NoSuchElementException();
      Object[] elementData = elements;
      if (i >= elementData.length)
        throw new ConcurrentModificationException();
      cursor = i + 1;
      return (T) elementData[lastRet = i];
    }

    public void remove() {
      if (lastRet < 0)
        throw new IllegalStateException();

      try {
        MyArrayList.this.remove(lastRet);
        cursor = lastRet;
        lastRet = -1;
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }
  }

  /**
   *  Конструктор по умолчанию.
   */
  public MyArrayList() {
    this.elements = new Object[DEFAULT_CAPACITY];
    size = 0;
  }

  /**
   *  Конструктор с указанием начального размера списка.
   */
  public MyArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      this.elements = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
      this.elements = EMPTY_DATA;
      size = 0;
    }
    throw new IllegalArgumentException("Illegal capacity: "+initialCapacity);
  }

  /**
   * Возвращает элемент с указанным индексом.
   * */
  public T get(int index) {
    rangeCheck(index);
    return (T) elements[index];
  }

  /**
   * Замещает элемент с указанным индексом на указанный элемент.
   * Возврашает замещенный элемент.
   */
  public T set(int index, T element) {
    rangeCheck(index);

    T oldValue = (T) elements[index];
    elements[index] = element;
    return oldValue;
  }

  /**
   * Возвращает текущее число элементов массива.
   * */
  public int size() {
    return size;
  }

  /**
   * Возвращает true, если список пустой, иначе false
   * */
  public boolean isEmpty(){
    return size == 0;
  }

  /**
   * Возвращает наименьший индекс указанного объекта,
   * если такой объект не найден, возвращает -1.
   * */
  public int indexOf(Object o) {
    if (o == null) {
      for (int i=0; i<size; i++)
        if (elements[i]==null)
          return i;
    } else {
      for (int i=0; i<size; i++) {
        if (o.equals(elements[i])) return i;
      }
    }
    return -1;
  }

  /**
   * Возвращает наибольший индекс указанного объекта,
   * если такой объект не найден, возвращает -1.
   * */
  public int lastIndexOf(Object o){
    if (o == null) {
      for (int i = size-1; i >= 0; i--)
        if (elements[i] == null)
          return i;
    } else {
      for (int i = size-1; i >= 0; i--)
        if (o.equals(elements[i]))
          return i;
    }
    return -1;
  }

  /*
  * Возвращает list iterator
  * */
  public ListIterator<T> listIterator() {
    return new ListItr(0);
  }

  /*
  * Возвращает list iterator начиная с указанной позиции в списке
  * */
  public ListIterator<T> listIterator(int idx) {
    if (idx < 0 || idx > size)
      throw new IndexOutOfBoundsException("Index: "+idx);
    return new ListItr(idx);
  }

  /*
  * метод  не реализован
  * */
  public List<T> subList(int i, int i1) {
    throw new RuntimeException("Not realised");
  }

  /**
   * Возвращает true, если указанный объект содержится в списке, иначе возвращает false.
   * */
  public boolean contains(Object o){
    return indexOf(o) >= 0;
  }

  /**
   * Возвращает итератор по списку
   */
  public Iterator<T> iterator(){
    return new Iter();
  }

  /**
   * Возвращает массив, содержащий все элементы списка в точной последовательности.
   */
  public Object[] toArray(){
    return Arrays.copyOf(elements, size);
  }

  /**
   * Возвращает элементы списка в виде массива типа T
   */
  public <T> T[] toArray(T[] a){
    if (a.length >= size) {
      System.arraycopy(elements, 0, a, 0, size);
      for (int i = size; i < a.length; i++) {
        a[i] = null;
      }
    } else {
      // длина a меньше elements
      // создаём и возвращаем массив нужного размера
      return (T[]) Arrays.copyOf(elements, size, a.getClass());
    }
    return a;
  }

  /**
   * добавление элемента в конец списка
   */
  public boolean add(T e){
    checkAndGrowCapacity(1);
    elements[size++] = e;
    return true;
  }

  /**
   * добавление элемента в указанную позицию в списке
   */
  public void add(int index, T e) {
    checkAndGrowCapacity(1);
    // увеличение размера массива
    size++;
    elements = Arrays.copyOf(elements, size);
    // сдвиг элементов
    System.arraycopy(elements, index, elements, index + 1,size - index - 1);
    // установка значения
    elements[index] = e;
  }

  /**
   * Удаляет элемент из списка.
   */
  public boolean remove(Object o){
    if (o == null) {
      for (int i = 0; i < size; i++) {
        if (elements[i] == null) {
          // количество переносимых элементов
          int cntToMove = size - i - 1;
          if (cntToMove > 0) {
            System.arraycopy(elements, i+1, elements, i, cntToMove);
            size--;
            elements[size] = null;
          }

          return true;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (elements[i].equals(o)) {
          // количество переносимых элементов
          int cntToMove = size - i - 1;
          if (cntToMove > 0) {
            System.arraycopy(elements, i + 1, elements, i, cntToMove);
            size--;
            elements[size] = null;
            return true;
          }
        }
      }
    }
    // элемент не найден
    return false;
  }

  /**
   * Удаляет и возвращает элемент с указанным индексом.
   */
  public T remove(int index) {
    rangeCheck(index);

    T removedElement = (T) elements[index];

    // количество переносимых элементов
    int cntToMove = size - index - 1;
    if (cntToMove > 0) {
      System.arraycopy(elements, index+1, elements, index, cntToMove);
      size--;
      elements[size] = null;
    }

    return removedElement;
  }

  /**
   * Проверка на вхождение всех элементов из указанной коллекции в список.
   */
  public boolean containsAll(Collection<?> c){
    if (c == null)
      throw new NullPointerException();

    Iterator it = c.iterator();
    while (it.hasNext()) {
      if (!contains(it.next()))
        return false;
    }
    return true;
  }

  /**
   * Добавление элементов коллекции к списку.
   */
  public boolean addAll(Collection<? extends T> c){
    if (c == null)
      throw new NullPointerException();

    Object[] a = c.toArray();
    int addedCount = a.length;

    checkAndGrowCapacity(addedCount);

    // расширение elements
    // добавление новых элементов
    System.arraycopy(a, 0, elements, size, addedCount);

    size = size + addedCount;
    return addedCount > 0;
  }

  /**
   * Добавление элементов коллекции к списку начиная с элемента с указанным индексом.
   */
  public boolean addAll(int i, Collection<? extends T> collection) {
    Object[] objArray = collection.toArray();
    int arrLength = objArray.length;
    checkAndGrowCapacity(this.size + arrLength);
    int idx = this.size - i;
    if (idx > 0) {
      System.arraycopy(this.elements, i, this.elements, i + arrLength, idx);
    }

    System.arraycopy(objArray, 0, this.elements, i, arrLength);
    this.size += arrLength;
    return arrLength != 0;
  }

  /**
   * Удаление из списка всех элементов, входящих в указанную коллекцию.
   */
  public boolean removeAll(Collection<?> c){
    if (c == null)
      throw new NullPointerException();

    boolean modified = false;
    int idx = 0;

    for (int i = 0; i < size; i++) {
      if (!c.contains(elements[i])) {
        elements[idx] = elements[i];
        idx++;
        modified = true;
      }
    }

    if (idx < size) {
      for (int i = idx; i < size; i++) {
        elements[i] = null;
      }
    }
    size = idx;

    return modified;
  }

  /**
   * Удаление из списка элементов, не содержащихся в указанной коллекции
   */
  public boolean retainAll(Collection<?> c){
    if (c == null)
      throw new NullPointerException();

    boolean modified = false;
    int idx = 0;

    for (int i = 0; i < size; i++) {
      if (c.contains(elements[i])) {
        elements[idx] = elements[i];
        idx++;
        modified = true;
      }
    }

    if (idx < size) {
      for (int i = idx; i < size; i++) {
        elements[i] = null;
      }
    }
    size = idx;

    return modified;
  }

  /**
   * удаляет все элементы списка
   */
  public void clear(){
    for (int i = 0; i < size; i++ ) {
      elements[i] = null;
    }
    size = 0;
  }

  /**
   * Уменьшение длины внутренненго массива до актуального размера списка.
   */
  public void trimToSize() {
    if (size < elements.length) {
      if (size == 0)
        elements = EMPTY_DATA;
      else
        elements = Arrays.copyOf(elements, size);
    }
  }

  /*
  * Сортировка элементов списка
  * */
  public void sort(Comparator<? super T> var1) {
    Arrays.sort((T[])this.elements, 0, this.size, var1);
  }

  private class ListItr extends Iter implements ListIterator<T> {

    ListItr(int index) {
      super();
      cursor = index;
    }

    public boolean hasPrevious() {
      return cursor != 0;
    }

    public T previous() {
      int i = cursor - 1;
      if (i < 0)
        throw new NoSuchElementException();
      Object[] elementData = MyArrayList.this.elements;
      if (i >= elementData.length)
        throw new ConcurrentModificationException();
      cursor = i;
      return (T) elementData[lastRet = i];
    }

    public int nextIndex() {
      return cursor;
    }

    public int previousIndex() {
      return cursor - 1;
    }

    public void set(T t) {
      if (lastRet < 0)
        throw new IllegalStateException();

      try {
        MyArrayList.this.set(lastRet, t);
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }

    public void add(T t) {
      try {
        int i = cursor;
        MyArrayList.this.add(i, t);
        cursor = i + 1;
        lastRet = -1;
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }

  }

}