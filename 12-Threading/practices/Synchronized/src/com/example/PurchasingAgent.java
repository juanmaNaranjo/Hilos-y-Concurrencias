package com.example;

public class PurchasingAgent {

    public PurchasingAgent() {
        System.out.printf("Creating a PurchasingAgent ");
    }

    public void purchase() {

        Store store = Store.getInstance();

        synchronized (store) {
            if (store.getShirtCount() > 0 &&
                    store.authorizeCreditCard("1234", 15.00)) {
                Shirt shirt = store.takeShirt();
                System.out.println("The shirt is ours!");
                System.out.println(shirt);
            } else {
                System.out.println("No shirt for you");
            }
        }

        // punto 6
        //Thread  t = Thread.currentThread();
        //System.out.println("Thread:"+ t.getName() + ","+ t.getId());




    }
}
