public class JobPost
{
    private String id,positionType,url,dateCreated,company,companyurl,location,title,description,howToApply,companyLogoImageURL;

    public JobPost(String id, String positionType, String url, String dateCreated, String company, String companyurl, String location, String title, String description, String howToApply, String companyLogoImageURL) {
        this.id = id;
        this.positionType = positionType;
        this.url = url;
        this.dateCreated = dateCreated;
        this.company = company;
        this.companyurl = companyurl;
        this.location = location;
        this.title = title;
        this.description = description;
        this.howToApply = howToApply;
        this.companyLogoImageURL = companyLogoImageURL;
    }

    @Override
    public String toString() {
        return "" + id + positionType + dateCreated + company + companyurl + location + title + description + howToApply + companyLogoImageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyurl() {
        return companyurl;
    }

    public void setCompanyurl(String companyurl) {
        this.companyurl = companyurl;
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

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    public String getCompanyLogoImageURL() {
        return companyLogoImageURL;
    }

    public void setCompanyLogoImageURL(String companyLogoImageURL) {
        this.companyLogoImageURL = companyLogoImageURL;
    }
}