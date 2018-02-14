
/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 11 - My cache engine
 */

import org.junit.*;
import ru.otus.smolyanov.cacheservice.*;

public class CacheServiceTest {

  private CacheService<String> cacheService;

  @Before
  public void setUp() {
    cacheService = new CacheServiceImpl.Builder<String>(3).build();
  }

  @After
  public void setDown() {
    cacheService.dispose();
  }

  @Test
  public void testCacheMaxSize() {
    cacheService.put(new ElementKey(1, String.class), "One");
    cacheService.put(new ElementKey(2, String.class), "Two");
    cacheService.put(new ElementKey(3, String.class), "Three");
    cacheService.put(new ElementKey(4, String.class), "Four");

    Assert.assertEquals(cacheService.getMissCount(), 1);
  }

  @Test
  public void testOneElement() {
    String value = "test value";
    cacheService.put(new ElementKey(1, String.class), value);

    Assert.assertEquals(cacheService.get(new ElementKey(1, String.class)),  value);
  }

  @Test
  public void testZeroSizeCache() {
    CacheService<String> zeroLengthCacheService = new CacheServiceImpl.Builder<String>(0).build();
    ElementKey key = new ElementKey(1, String.class);
    zeroLengthCacheService.put(key, "test value");

    Assert.assertNull(zeroLengthCacheService.get(key));
  }
}
