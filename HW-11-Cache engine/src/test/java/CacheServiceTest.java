
/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

import org.junit.*;
import ru.otus.smolyanov.cacheservice.*;

public class CacheServiceTest {

  private CacheService<Integer, String> cacheService;

  @Before
  public void setUp() {
    cacheService = new CacheServiceImpl<>(3, 0, 0, true);
  }

  @After
  public void setDown() {
    cacheService.dispose();
  }

  @Test
  public void testCacheMaxSize() {
    cacheService.put(new Element<>(1, "One"));
    cacheService.put(new Element<>(2, "Two"));
    cacheService.put(new Element<>(3, "Three"));
    cacheService.put(new Element<>(4, "Four"));

    Assert.assertEquals(cacheService.getMissCount(), 1);
  }

  @Test
  public void testOneElement() {
    String value = "test value";
    Element<Integer, String> element = new Element<>(1, value);
    cacheService.put(element);

    Assert.assertEquals(cacheService.get(1).getValue(),  value);
  }

  @Test
  public void testZeroSizeCache() {
    CacheService<Integer, String> zeroLengthCacheService = new CacheServiceImpl<>(0, 0, 0, true);

    Element<Integer, String> element = new Element<>(1, "test value");
    zeroLengthCacheService.put(element);

    Assert.assertNull(zeroLengthCacheService.get(1));
  }
}
