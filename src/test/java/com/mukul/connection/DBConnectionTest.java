package com.mukul.connection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.Statement;

import static org.mockito.Mockito.*;

public class DBConnectionTest {
    @InjectMocks
    private DBConnection dbConnection;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBConnection() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.createStatement().executeUpdate(any())).thenReturn(1);
        int value = dbConnection.executeQuery("");
        Assert.assertEquals(value, 1);
        verify(mockConnection.createStatement(), times(1));
    }
}