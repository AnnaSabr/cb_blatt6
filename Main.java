import java.util.ArrayList;
import java.util.List;

// Singleton Heap-Klasse
class Heap {
    private static Heap instance;
    private List<Object> storage;  // Der "Heap" speichert die Objekte
    private List<Boolean> freeSpaces;  // Verfolgt, ob ein Speicherplatz frei ist
    private int maxSize;  // Maximale Anzahl der Objekte im Heap

    // Privater Konstruktor für das Singleton
    private Heap(int maxSize) {
        this.maxSize = maxSize;
        this.storage = new ArrayList<>(maxSize);
        this.freeSpaces = new ArrayList<>(maxSize);

        // Initialisieren des Heaps und der freien Plätze
        for (int i = 0; i < maxSize; i++) {
            storage.add(null);  // Platz für Objekte im Heap
            freeSpaces.add(true);  // Alle Plätze sind anfangs frei
        }
    }

    // Statische Methode, um die einzige Instanz des Heaps zu bekommen
    public static Heap getInstance(int maxSize) {
        if (instance == null) {
            instance = new Heap(maxSize);
        }
        return instance;
    }

    // Methode zum Reservieren von Speicherplatz (analog zu "new")
    public <T> Pointer<T> newObject(T object) {
        for (int i = 0; i < maxSize; i++) {
            if (freeSpaces.get(i)) {
                storage.set(i, object);  // Objekt speichern
                freeSpaces.set(i, false);  // Platz als belegt markieren
                return new Pointer<>(i);  // Rückgabe eines Pointers auf den Speicherplatz
            }
        }
        throw new OutOfMemoryError("Heap ist voll");
    }

    // Methode zum Freigeben eines Speicherplatzes (analog zu "delete")
    public <T> void delete(Pointer<T> pointer) {
        int index = pointer.getIndex();
        if (index >= 0 && index < maxSize) {
            storage.set(index, null);  // Objekt entfernen
            freeSpaces.set(index, true);  // Platz als frei markieren
        }
    }

    // Methode zum Abrufen eines Objekts aus dem Heap
    public Object getObjectAtIndex(int index) {
        if (index >= 0 && index < maxSize) {
            return storage.get(index);
        }
        return null;
    }
}

// Abstrakte Basis-Klasse für Objekte im Heap
abstract class HeapObject {
    // Gemeinsame Eigenschaften und Methoden für alle Objekte im Heap
}

// Pointer-Klasse, die auf ein Objekt im Heap zeigt
class Pointer<T> {
    private int index;  // Der Index im Heap, der auf das Objekt zeigt

    // Konstruktor
    public Pointer(int index) {
        this.index = index;
    }

    // Methode zum Dereferenzieren (Zugriff auf das Objekt im Heap)
    public T dereference() {
        Heap heap = Heap.getInstance(0);  // Erhalten der Heap-Instanz (Parameter ist irrelevant)
        @SuppressWarnings("unchecked")
        T object = (T) heap.getObjectAtIndex(index);  // Objekt aus dem Heap holen
        return object;
    }

    // Getter für den Index
    public int getIndex() {
        return index;
    }
}

// Beispielklasse für Objekte im Heap (z. B. Foo)
class Foo extends HeapObject {
    private String name;

    public Foo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Foo{name='" + name + "'}";
    }
}

// Eine weitere Beispielklasse für Objekte im Heap (z. B. Bar)
class Bar extends HeapObject {
    private int id;

    public Bar(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Bar{id=" + id + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        // Test 1: Erstellen und Dereferenzieren eines Foo-Objekts
        System.out.println("Test 1: Erstellen und Dereferenzieren eines Foo-Objekts");
        Heap heap1 = Heap.getInstance(5);
        Foo foo1 = new Foo("Test Foo 1");
        Pointer<Foo> pointerFoo1 = heap1.newObject(foo1);
        System.out.println("Dereferenzierter Pointer Foo 1: " + pointerFoo1.dereference());

        // Test 2: Erstellen und Dereferenzieren eines Bar-Objekts
        System.out.println("\nTest 2: Erstellen und Dereferenzieren eines Bar-Objekts");
        Bar bar1 = new Bar(42);
        Pointer<Bar> pointerBar1 = heap1.newObject(bar1);
        System.out.println("Dereferenzierter Pointer Bar 1: " + pointerBar1.dereference());

        // Test 3: Löschen eines Objekts (Foo) und Zugriff auf den Pointer danach
        System.out.println("\nTest 3: Löschen eines Foo-Objekts und Zugriff auf den Pointer danach");
        heap1.delete(pointerFoo1);  // Löschen des Foo-Objekts
        System.out.println("Pointer Foo 1 nach Löschen: " + pointerFoo1.dereference());  // Sollte null sein

        // Test 4: Löschen des Bar-Objekts und Zugriff auf den Pointer danach
        System.out.println("\nTest 4: Löschen eines Bar-Objekts und Zugriff auf den Pointer danach");
        heap1.delete(pointerBar1);  // Löschen des Bar-Objekts
        System.out.println("Pointer Bar 1 nach Löschen: " + pointerBar1.dereference());  // Sollte null sein

        // Test 5: Heap ist voll - neues Objekt anlegen
        System.out.println("\nTest 5: Heap ist voll - neues Objekt anlegen");
        heap1 = Heap.getInstance(2);  // Heap mit maximaler Größe 2
        Foo foo2 = new Foo("Test Foo 2");
        Pointer<Foo> pointerFoo2 = heap1.newObject(foo2);
        System.out.println("Dereferenzierter Pointer Foo 2: " + pointerFoo2.dereference());

        // Versuchen, ein weiteres Objekt zu erstellen, wenn der Heap voll ist
        try {
            Bar bar2 = new Bar(7);
            Pointer<Bar> pointerBar2 = heap1.newObject(bar2);  // Sollte eine OutOfMemoryError werfen
        } catch (OutOfMemoryError e) {
            System.out.println("Fehler beim Erstellen eines neuen Objekts: " + e.getMessage());
        }

        // Test 6: Objekt nach dem Löschen eines Objekts hinzufügen
        System.out.println("\nTest 6: Objekt nach dem Löschen eines Objekts hinzufügen");
        heap1.delete(pointerFoo2);  // Löschen des Foo-Objekts
        Bar bar3 = new Bar(99);  // Jetzt kann wieder ein Objekt eingefügt werden
        Pointer<Bar> pointerBar3 = heap1.newObject(bar3);
        System.out.println("Dereferenzierter Pointer Bar 3 nach Löschen von Foo 2: " + pointerBar3.dereference());

        // Test 7: Dereferenzieren eines gelöschten Objekts
        System.out.println("\nTest 7: Dereferenzieren eines gelöschten Objekts");
        try {
            System.out.println("Dereferenzieren des gelöscht Foo 2: " + pointerFoo2.dereference());
        } catch (NullPointerException e) {
            System.out.println("Fehler: Dereferenzieren eines gelöschten Objekts.");
        }

        // Test 8: Mehrere Objekte erstellen und löschen
        System.out.println("\nTest 8: Mehrere Objekte erstellen und löschen");
        Heap heap2 = Heap.getInstance(3);  // Heap mit maximaler Größe 3
        Foo foo3 = new Foo("Test Foo 3");
        Pointer<Foo> pointerFoo3 = heap2.newObject(foo3);
        System.out.println("Dereferenzierter Pointer Foo 3: " + pointerFoo3.dereference());

        Bar bar4 = new Bar(88);
        Pointer<Bar> pointerBar4 = heap2.newObject(bar4);
        System.out.println("Dereferenzierter Pointer Bar 4: " + pointerBar4.dereference());

        Foo foo4 = new Foo("Test Foo 4");
        Pointer<Foo> pointerFoo4 = heap2.newObject(foo4);
        System.out.println("Dereferenzierter Pointer Foo 4: " + pointerFoo4.dereference());

        // Löschen von Foo 3 und Bar 4
        heap2.delete(pointerFoo3);
        heap2.delete(pointerBar4);
        System.out.println("Nach Löschen von Foo 3 und Bar 4:");
        System.out.println("Dereferenzierter Pointer Foo 3: " + pointerFoo3.dereference());  // null
        System.out.println("Dereferenzierter Pointer Bar 4: " + pointerBar4.dereference());  // null

        // Noch ein Objekt hinzufügen (Nach Löschen der Objekte sollte Platz sein)
        Foo foo5 = new Foo("Test Foo 5");
        Pointer<Foo> pointerFoo5 = heap2.newObject(foo5);
        System.out.println("Dereferenzierter Pointer Foo 5 nach Löschen: " + pointerFoo5.dereference());
    }
}


