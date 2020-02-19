public class StackOverFlowJobPost {
  // The gsonXML serializer requires single declarations to help with odd names and sub categories
  private String guid;
  private String link;
  private String author;
  private String category;
  private String title;
  private String description;
  private String pubDate;
  private String location;

  public StackOverFlowJobPost(
      String guid,
      String link,
      String author,
      String category,
      String title,
      String description,
      String pubDate,
      String location) {
    this.guid = guid;
    this.link = link;
    this.author = author;
    this.category = category;
    this.title = title;
    this.description = description;
    this.pubDate = pubDate;
    this.location = location;
  }

  public StackOverFlowJobPost() {}

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPubDate() {
    return pubDate;
  }

  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
