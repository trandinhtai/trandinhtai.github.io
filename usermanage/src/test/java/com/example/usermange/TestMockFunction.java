package com.example.usermange;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
@ExtendWith(MockitoExtension.class)
public class TestMockFunction {
    @Mock
    List<String> mockList;
    @Test
    public void test() {
        // Mock một List
        List mockList = Mockito.mock(List.class);
        mockList.add("one");

        // Giả định khi gọi đến method size của list đã mock thì sẽ về 2
        Mockito.when(mockList.size()).thenReturn(2);

        // Sử dụng Junit kiểm tra size của list đã mock có phải bằng 2
        Assert.assertEquals(2, mockList.size());
        Assert.assertEquals(0, mockList.size());
    }
}
