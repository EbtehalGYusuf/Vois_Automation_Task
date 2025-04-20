import com.utils.Configurations;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.*;

@Epic("YouTube Search")
@Feature("Video Selection")
@Listeners({AllureTestNg.class, com.utils.AllureTestListener.class})
public class searchInYoutube extends Configurations {

  @Test(
      enabled = true,
      dataProvider = "searchData",
      dataProviderClass = com.utils.TestDataProvider.class)
  @Description("Search, Filter and Select Specific Video From Youtube")
  public void case01_SearchFilterSelectSpecificVideo(String searchText)
      throws InterruptedException {
    System.out.println(
        "Thread ID: " + Thread.currentThread().getId() + " | Driver: " + getDriver());
    getHomePage().waitForPageLoading();
    getHomePage().search(searchText);
    getHomePage().clickonFilter();
    getHomePage().selectTypeFilter("video");
    getHomePage().selectVideo(3);
  }

  @Test(
      enabled = true,
      dataProvider = "searchData",
      dataProviderClass = com.utils.TestDataProvider.class)
  @Description("Search, Filter and Select Specific Video From Youtube")
  public void case02_SearchFilterSelectSpecificVideo(String searchText)
      throws InterruptedException {
    System.out.println(
        "Thread ID: " + Thread.currentThread().getId() + " | Driver: " + getDriver());
    getHomePage().waitForPageLoading();
    getHomePage().search(searchText);
    getHomePage().clickonFilter();
    getHomePage().selectTypeFilter("video");
    getHomePage().selectVideo(3);
  }

  @Test(
      enabled = true,
      dataProvider = "searchData",
      dataProviderClass = com.utils.TestDataProvider.class)
  @Description("Search, Filter and Select Specific Video From Youtube")
  public void case03_SearchFilterSelectSpecificVideo(String searchText)
      throws InterruptedException {
    System.out.println(
        "Thread ID: " + Thread.currentThread().getId() + " | Driver: " + getDriver());
    getHomePage().waitForPageLoading();
    getHomePage().search(searchText);
    getHomePage().clickonFilter();
    getHomePage().selectTypeFilter("video");
    getHomePage().selectVideo(10);
  }
}
