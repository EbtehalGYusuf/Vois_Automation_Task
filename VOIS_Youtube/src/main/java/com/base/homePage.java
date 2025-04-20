package com.base;

import com.core.homePageCore;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class homePage {
  protected WebDriver driver;

  private homePageCore homeP;

  public homePage(WebDriver driver) {
    this.homeP = new homePageCore(driver); 
  }

  public void waitForPageLoading() {
    homeP.waitForPageLoading();
  }

  @Step("Search YouTube for: {searchText}")
  public void search(String searchText) throws InterruptedException {
    homeP.search(searchText);
  }

  @Step("Clear Search")
  public void clearSearch() {
    homeP.clearSearch();
  }

  @Step("Get search result title at index {videoIndex}")
  public String getSearchResultTitle(int videoIndex) {
    return homeP.getSearchResultTitle(videoIndex);
  }

  @Step("Select video at index {videoIndex}")
  public void selectVideo(int videoIndex) {
    homeP.selectVideo(videoIndex);
  }

  @Step("Get stream title of the currently playing video")
  public String getVideoStreamTitle() {
    return homeP.getVideoStreamTitle();
  }

  @Step("Get title of video in search results at index {videoIndex}")
  public String getVideoTitle(int videoIndex) {
    return homeP.getVideoTitle(videoIndex);
  }

  @Step("Click on the 'Filter' button")
  public void clickonFilter() throws InterruptedException {
    homeP.clickonFilter();
  }

  @Step("Select filter type: {typeFilter}")
  public void selectTypeFilter(String typeFilter) {
    homeP.selectTypeFilter(typeFilter);
  }
}
