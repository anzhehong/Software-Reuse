package com.API.Controller;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by fowafolo
 * Date: 16/4/6
 * Time: 18:50
 */
public class DBAPITest {

    @Test
    public void testCheckPassword() throws Exception {
        assertEquals(true,DBAPI.CheckPassword("abc","abc"));
        assertEquals(false,DBAPI.CheckPassword("abc","abcc"));
        assertEquals(true,DBAPI.CheckPassword("cba","cba"));
    }

    @Test
    public void testRegisterUser() throws Exception {

    }
}