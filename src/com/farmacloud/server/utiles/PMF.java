package com.farmacloud.server.utiles;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF 
{
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");
    /* transactions-optional hace ref al modo del fichero .xml */

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
