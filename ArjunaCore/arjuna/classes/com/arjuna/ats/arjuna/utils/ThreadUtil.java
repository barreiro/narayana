/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors 
 * as indicated by the @author tags. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package com.arjuna.ats.arjuna.utils;

/**
 * Provides utilities to manage thread ids.
 */
public class ThreadUtil
{

    private static ThreadGroup rootThreadGroup = null;

    private static ThreadGroup getRootThreadGroup( ) {
        if ( rootThreadGroup == null ) {
            ThreadGroup tg = Thread.currentThread().getThreadGroup();
            for ( ThreadGroup ptg = tg.getParent(); ptg != null; ptg = tg.getParent() ) {
                tg = ptg;
            }
            rootThreadGroup = tg;
        }
        return rootThreadGroup;
    }

    private static Thread[] getAllThreads( ) {
        ThreadGroup root = getRootThreadGroup( );
        int nAlloc = java.lang.management.ManagementFactory.getThreadMXBean().getThreadCount();
        int n;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[ nAlloc ];
            n = root.enumerate( threads, true );
        } while ( n == nAlloc );
        return java.util.Arrays.copyOf( threads, n );
    }

    /**
     * Get the thread for a specified ID, as returned by Thread.getId()
     *
     * @param id The thread id
     * @return The Thread 
     */
    public static Thread getThread( long id ) {
        Thread[] threads = getAllThreads();
        for ( Thread thread : threads ) {
            if ( thread.getId() == id ) {
                return thread;
            }
        }
        return null;
    }

}
