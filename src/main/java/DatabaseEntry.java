public class DatabaseEntry {

  private String id;
  private String type;
  private String url;
  private String created_at;
  private String company;
  private String company_url;
  private String location;
  private String title;
  private String description;
  private String how_to_apply;
  private String company_logo;
  private String category;
  private String longitude;
  private String latitude;

  public DatabaseEntry() {}

  public DatabaseEntry(
      String id,
      String type,
      String url,
      String created_at,
      String company,
      String company_url,
      String location,
      String title,
      String description,
      String how_to_apply,
      String company_logo,
      String category,
      String longitude,
      String latitude) {
    this.id = id;
    this.type = type;
    this.url = url;
    this.created_at = created_at;
    this.company = company;
    this.company_url = company_url;
    this.location = location;
    this.title = title;
    this.description = description;
    this.how_to_apply = how_to_apply;
    this.company_logo = company_logo;
    this.category = category;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  @Override
  public String toString() {
    return "DatabaseEntry{"
        + "id='"
        + id
        + '\''
        + ", type='"
        + type
        + '\''
        + ", url='"
        + url
        + '\''
        + ", created_at='"
        + created_at
        + '\''
        + ", company='"
        + company
        + '\''
        + ", company_url='"
        + company_url
        + '\''
        + ", location='"
        + location
        + '\''
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", how_to_apply='"
        + how_to_apply
        + '\''
        + ", company_logo='"
        + company_logo
        + '\''
        + ", category='"
        + category
        + '\''
        + ", longitude='"
        + longitude
        + '\''
        + ", latitude='"
        + latitude
        + '\''
        + '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompany_url() {
    return company_url;
  }

  public void setCompany_url(String company_url) {
    this.company_url = company_url;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
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

  public String getHow_to_apply() {
    return how_to_apply;
  }

  public void setHow_to_apply(String how_to_apply) {
    this.how_to_apply = how_to_apply;
  }

  public String getCompany_logo() {
    return company_logo;
  }

  public void setCompany_logo(String company_logo) {
    this.company_logo = company_logo;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }
}
