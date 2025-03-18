package daj.common.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class AppPageTest {

  @Test
  void carefulWithDivideBetweenIntegers() throws Exception {
    final List<Object> content = generateContent(10);
    final AppPage<Object> page = new AppPage<Object>(content, 21, 10, 10);
    assertEquals(3, page.getTotalPages());
  }

  @Test
  void checkBugNumber999999999999999() throws Exception {
    final List<Object> content = generateContent(1);
    final AppPage<Object> page = new AppPage<Object>(content, 21, 2, 10);
    assertEquals(3, page.getTotalPages());
  }

  private List<Object> generateContent(int size) {
    final List<Object> content = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      content.add(new Object());
    }
    return content;
  }


}
