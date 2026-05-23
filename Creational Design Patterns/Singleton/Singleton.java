/*
    ============================================================
                    SINGLETON DESIGN PATTERN
    ============================================================

    Singleton ensures:
    -> Only ONE object of a class exists
    -> Global access point to that object

    Common real-world use cases:
    -> Logger
    -> Database Connection Pool
    -> Cache Manager
    -> Configuration Manager
    -> Thread Pool Manager
*/


/*
    ============================================================
                    1. EAGER INITIALIZATION
    ============================================================

    Object is created immediately when class loads.

    Pros:
    -> Simple
    -> Thread-safe (JVM class loading is thread-safe)

    Cons:
    -> Object created even if never used
*/

class EagerSingleton {

    // Static final -> only one immutable shared instance
    private static final EagerSingleton instance =
            new EagerSingleton();

    // Private constructor prevents outside object creation
    private EagerSingleton() {
        System.out.println("EagerSingleton Constructor Called");
    }

    // Global access point
    public static EagerSingleton getInstance() {
        return instance;
    }
}



/*
    ============================================================
                    2. LAZY INITIALIZATION
    ============================================================

    Object created only when first requested.

    Pros:
    -> Memory efficient
    -> Lazy loading

    Cons:
    -> NOT thread-safe
*/

class LazySingleton {

    // Initially null -> object not created yet
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("LazySingleton Constructor Called");
    }

    public static LazySingleton getInstance() {

        // Object created only during first call
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}



/*
    ============================================================
            3. THREAD-SAFE LAZY SINGLETON
    ============================================================

    synchronized keyword prevents multiple threads
    from creating multiple objects simultaneously.

    Pros:
    -> Thread-safe
    -> Lazy loading

    Cons:
    -> Every call acquires lock
    -> Performance overhead
*/

class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
        System.out.println("SynchronizedSingleton Constructor Called");
    }

    // synchronized allows only one thread at a time
    public static synchronized SynchronizedSingleton getInstance() {

        if (instance == null) {
            instance = new SynchronizedSingleton();
        }

        return instance;
    }
}



/*
    ============================================================
                4. DOUBLE CHECKED LOCKING
    ============================================================

    Optimization over synchronized singleton.

    Locking happens ONLY during first initialization.

    volatile is VERY important here:
    -> Prevents instruction reordering
    -> Ensures memory visibility across threads

    Pros:
    -> Thread-safe
    -> Better performance
    -> Lazy loading

    Cons:
    -> More complex
*/

class DoubleCheckedLockingSingleton {

    // volatile prevents partially initialized object issue
    private static volatile DoubleCheckedLockingSingleton instance;

    private DoubleCheckedLockingSingleton() {
        System.out.println("DoubleCheckedLockingSingleton Constructor Called");
    }

    public static DoubleCheckedLockingSingleton getInstance() {

        // First check without locking (fast path)
        if (instance == null) {

            // Lock acquired only when object not initialized
            synchronized (DoubleCheckedLockingSingleton.class) {

                // Second check prevents duplicate creation
                if (instance == null) {

                    instance =
                            new DoubleCheckedLockingSingleton();
                }
            }
        }

        return instance;
    }
}



/*
    ============================================================
                    5. BILL PUGH SINGLETON
    ============================================================

    Uses JVM class loading mechanism internally.

    Holder class loads only when first referenced.

    JVM guarantees:
    -> Class loading is thread-safe
    -> Static initialization happens once

    Pros:
    -> Thread-safe
    -> Lazy loading
    -> No synchronization overhead
    -> Cleaner implementation

    Most recommended interview implementation.
*/

class BillPughSingleton {

    private BillPughSingleton() {
        System.out.println("BillPughSingleton Constructor Called");
    }

    /*
        Static nested class is NOT loaded
        until getInstance() is called.
    */
    private static class Holder {

        // Singleton instance created during Holder loading
        private static final BillPughSingleton instance =
                new BillPughSingleton();
    }

    // Access point for Singleton object
    public static BillPughSingleton getInstance() {

        return Holder.instance;
    }
}



/*
    ============================================================
                            MAIN CLASS
    ============================================================

    Demonstrating all Singleton implementations.
*/

public class Singleton {

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("EAGER SINGLETON");
        System.out.println("================================================");

        EagerSingleton eager1 =
                EagerSingleton.getInstance();

        EagerSingleton eager2 =
                EagerSingleton.getInstance();

        System.out.println(eager1);
        System.out.println(eager2);

        System.out.println(
                "Same Object ? -> " + (eager1 == eager2)
        );


        System.out.println("\n================================================");
        System.out.println("LAZY SINGLETON");
        System.out.println("================================================");

        LazySingleton lazy1 =
                LazySingleton.getInstance();

        LazySingleton lazy2 =
                LazySingleton.getInstance();

        System.out.println(lazy1);
        System.out.println(lazy2);

        System.out.println(
                "Same Object ? -> " + (lazy1 == lazy2)
        );


        System.out.println("\n================================================");
        System.out.println("SYNCHRONIZED SINGLETON");
        System.out.println("================================================");

        SynchronizedSingleton sync1 =
                SynchronizedSingleton.getInstance();

        SynchronizedSingleton sync2 =
                SynchronizedSingleton.getInstance();

        System.out.println(sync1);
        System.out.println(sync2);

        System.out.println(
                "Same Object ? -> " + (sync1 == sync2)
        );


        System.out.println("\n================================================");
        System.out.println("DOUBLE CHECKED LOCKING SINGLETON");
        System.out.println("================================================");

        DoubleCheckedLockingSingleton dcl1 =
                DoubleCheckedLockingSingleton.getInstance();

        DoubleCheckedLockingSingleton dcl2 =
                DoubleCheckedLockingSingleton.getInstance();

        System.out.println(dcl1);
        System.out.println(dcl2);

        System.out.println(
                "Same Object ? -> " + (dcl1 == dcl2)
        );


        System.out.println("\n================================================");
        System.out.println("BILL PUGH SINGLETON");
        System.out.println("================================================");

        BillPughSingleton bp1 =
                BillPughSingleton.getInstance();

        BillPughSingleton bp2 =
                BillPughSingleton.getInstance();

        System.out.println(bp1);
        System.out.println(bp2);

        System.out.println(
                "Same Object ? -> " + (bp1 == bp2)
        );
    }
}