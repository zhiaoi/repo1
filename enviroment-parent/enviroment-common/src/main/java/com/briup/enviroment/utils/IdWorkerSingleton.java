package com.briup.enviroment.utils;


public class IdWorkerSingleton {
    
    private static IdWorkerSingleton instance;
    private IdWorkerSingleton() {}
    
    public static synchronized IdWorkerSingleton getIdWorker() {
        
        if (instance == null) {
            synchronized (IdWorkerSingleton.class) {
                if (instance == null) {
                    instance = new IdWorkerSingleton();
                }
            }
        }
        
        return instance;
    }
    
}
