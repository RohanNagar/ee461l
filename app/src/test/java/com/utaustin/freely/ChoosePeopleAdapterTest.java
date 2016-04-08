package com.utaustin.freely;

import com.google.common.collect.Lists;
import com.utaustin.freely.adapters.ChoosePeopleAdapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChoosePeopleAdapterTest {
    private final ArrayList<String> people = Lists.newArrayList("1", "2", "3");
    private final ChoosePeopleAdapter adapter = new ChoosePeopleAdapter(people);

    @Test
    public void testGetItemCount() {
        assertEquals(people.size(), adapter.getItemCount());
    }
}
