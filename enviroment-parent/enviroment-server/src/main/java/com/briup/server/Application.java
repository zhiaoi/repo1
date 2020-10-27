package com.briup.server;

import com.briup.server.revice.IRevicer;
import com.briup.server.revice.ReviceImpl;

public class Application {
    public static void main(String[] args) {
        IRevicer reviceImpl = new ReviceImpl();
        reviceImpl.revice();
    }
}
