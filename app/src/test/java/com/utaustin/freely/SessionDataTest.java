package com.utaustin.freely;

import com.utaustin.freely.data.SessionData;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionDataTest {
    private final SessionData data = new SessionData("name", "id");

    @Test
    public void testGetName() {
        assertEquals(data.getName(), "name");
    }

    @Test
    public void testGetId() {
        assertEquals(data.getId(), "id");
    }
}
