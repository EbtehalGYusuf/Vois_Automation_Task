package com.core;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class homePageCore {
  private WebDriver driver;
  private WebDriverWait wait;

  private static final By Video = By.xpath(".//div[contains(@title,'Video')]");
  private static final By Channel = By.xpath(".//div[contains(@title,'Channel')]");
  private static final By Playlist = By.xpath(".//div[contains(@title,'Playlist')]");
  private static final By Film = By.xpath(".//div[contains(@title,'Film')]");
  private static final By SEARCHRESULT = By.xpath("//a[@id='video-title']");

  @FindBy(xpath = "//yt-searchbox//button[@aria-label='Search']")
  private WebElement searchLens;

  @FindBy(xpath = "//input[@name='search_query']")
  private WebElement searchBox;

  @FindBy(xpath = "//button[contains(@aria-label,'Clear')]")
  private WebElement clearSearch;

  @FindBy(xpath = "//a[@id='video-title']")
  private List<WebElement> searchResults;

  @FindBy(xpath = "//div[@class='ytSearchboxComponentSuggestionsContainer']")
  private WebElement searchSuggestions;

  @FindBy(
      xpath = "//div[@class='ytSearchboxComponentSuggestionsContainer']//div[@role='presentation']")
  private List<WebElement> searchSuggestionsList;

  @FindBy(
      xpath =
          "//div[contains(@class,'metadata-view-model')]//span[contains(@class,'yt-core-attributed-string')]")
  private List<WebElement> noSearchResults;

  @FindBy(xpath = "//div[@id='filter-button']//button")
  private WebElement filterButton;

  @FindBy(
      xpath =
          "//yt-formatted-string[text()='Type']/parent::h4[@id='filter-group-name']/following-sibling::ytd-search-filter-renderer")
  private List<WebElement> typeFilterList;

  @FindBy(xpath = "//button[@aria-label='Cancel']")
  private WebElement closePopup;

  @FindBy(xpath = "//video[@tabindex]")
  private WebElement video;

  @FindBy(xpath = "//div[@id='title']//yt-formatted-string[@title]")
  private WebElement videoStreamTitle;

  public homePageCore(WebDriver driver) {
    this.driver = driver;
    //        this.driver = driver;
    //        this.driver = DriverFactory.getDriver();  // Get driver from ThreadLocal context
    this.driver = driver; // Use the driver passed from homePage, not from DriverFactory

    PageFactory.initElements(driver, this);
    wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  }

  // public homePageCore initialize(WebDriver driver){
  //        return  PageFactory.initElements(driver, homePageCore.class);
  // }

  public void search(String searchText) {
    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
    String text = searchBox.getText();
    if (!searchBox.getText().isEmpty()) {
      clearSearch.click();
    }
    searchBox.sendKeys(searchText);
    searchLens.click();
    wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
  }

  public void clearSearch() {
    clearSearch.click();
  }

  public String getSearchResultTitle(int videoIndex) {
    int i = videoIndex - 1;
    WebElement selectedVideo = searchResults.get(i);
    String videoTitle = selectedVideo.getAttribute("title");
    return videoTitle;
  }

  public void waitForPageLoading() {
    wait.until(
        driver ->
            ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .equals("complete"));
  }

  public void selectVideo(int videoIndex) {
    int i = videoIndex - 1;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
      // Scroll the video into view
      js.executeScript("arguments[0].scrollIntoView(true);", searchResults.get(i));

      // Wait until the element is clickable
      wait.until(ExpectedConditions.elementToBeClickable(searchResults.get(i)));

      // Click the element
      searchResults.get(i).click();

      // Wait until the video element is visible
      wait.until(ExpectedConditions.visibilityOfAllElements(video));
    } catch (StaleElementReferenceException e) {
      // If stale, re-locate the element and retry
      System.out.println("Stale Element Reference, retrying...");

      // Re-locate the element and retry clicking
      List<WebElement> videoElement =
          driver.findElements(
              SEARCHRESULT); // re-locate element by XPath or another suitable selector
      videoElement.get(i).click();

      // Wait until the video is visible
      wait.until(ExpectedConditions.visibilityOfAllElements(video));
    } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException t) {
      // Handle other exceptions
      System.out.println("Exception occurred while selecting Video: " + t.getMessage());
    }
  }

  // get title of selected video
  public String getVideoStreamTitle() {
    String videoTitle = videoStreamTitle.getAttribute("title");
    return videoTitle;
  }

  // get any video title in search
  public String getVideoTitle(int videoIndex) {
    int i = videoIndex - 1;
    WebElement selectedVideo = searchResults.get(i);
    String videoTitle = selectedVideo.getAttribute("title");
    return videoTitle;
  }

  // clicking on Filters
  public void clickonFilter() throws InterruptedException {
    wait.until(ExpectedConditions.visibilityOf(filterButton));
    wait.until(ExpectedConditions.elementToBeClickable(filterButton));
    filterButton.click();
    wait.until(ExpectedConditions.visibilityOfAllElements(typeFilterList));
  }

  // selecting type inside Filters
  public void selectTypeFilter(String typeFilter) {
    String lowerCaseInput = typeFilter.toLowerCase();

    // Wait for the filters to be visible and clickable
    wait.until(ExpectedConditions.visibilityOfAllElements(typeFilterList));

    WebElement filterToClick = null;

    // Iterate over the filter list and select the appropriate one based on user input
    switch (lowerCaseInput) {
      case "video":
        filterToClick = typeFilterList.get(0).findElement(Video);
        break;
      case "channel":
        filterToClick = typeFilterList.get(1).findElement(Channel);
        break;
      case "playlist":
        filterToClick = typeFilterList.get(2).findElement(Playlist);
        break;
      case "film":
        filterToClick = typeFilterList.get(3).findElement(Film);
        break;
      default:
        System.out.println("User entered a different input.");
        return;
    }
    wait.until(ExpectedConditions.elementToBeClickable(filterToClick));
    filterToClick.click();
    // Wait until search results are clickable after filter is applied
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(SEARCHRESULT));
  }
}
