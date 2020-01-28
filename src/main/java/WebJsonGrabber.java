import com.google.gson.*;

import java.util.Vector;

public class WebJsonGrabber {
    public static void main(String[] args)
    {
        System.out.println("Hello World");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String tesJson = "{\n" +
                "    \"id\": \"cd535970-ec11-11e8-89ae-2e904411ec0e\",\n" +
                "    \"type\": \"Full Time\",\n" +
                "    \"url\": \"https://jobs.github.com/positions/cd535970-ec11-11e8-89ae-2e904411ec0e\",\n" +
                "    \"created_at\": \"Mon Nov 19 15:44:44 UTC 2018\",\n" +
                "    \"company\": \"Aon Cyber Solutions\",\n" +
                "    \"company_url\": \"http://www.strozfriedberg.com/careers\",\n" +
                "    \"location\": \"NY or LA\",\n" +
                "    \"title\": \"Cybersecurity Firm Seeks Senior Developer\",\n" +
                "    \"description\": \"\\u003cp\\u003eAon Cyber Solutions is looking for a Senior Developer in NY or LA. As part of an industry-leading team, you will help empower results for our clients by delivering innovative and effective solutions supporting Risk.\\u003c/p\\u003e\\n\\u003cp\\u003eAPPLY HERE: \\u003ca href=\\\"https://us-strozfriedberg-aon.icims.com/jobs/24315/senior-developer/job\\\"\\u003ehttps://us-strozfriedberg-aon.icims.com/jobs/24315/senior-developer/job\\u003c/a\\u003e\\u003c/p\\u003e\\n\\u003cp\\u003ePosition Overview\\nJoin our expert software development team to create new customer-facing web applications. You will apply your expertise in web development and user interface design, working in concert with subject matter experts in digital forensics, incident response, cybersecurity, and threat intelligence. Your applications will convey deeply technical facts and analyses to a variety of consumers, from risk managers to subject matter experts to C-level executives.\\u003c/p\\u003e\\n\\u003cp\\u003eThe responsibilities of this position include but are not limited to the following:\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eDesign and develop pioneering web applications that cross the boundaries of security assessment, digital forensics, incident response, malware analysis, cybersecurity, and cyberinsurance.\\u003c/li\\u003e\\n\\u003cli\\u003eServe as a senior developer on multiple product teams.\\u003c/li\\u003e\\n\\u003cli\\u003eCreate user interfaces, APIs, data visualizations, and reports that provide our clients with insight into complex narratives and data sets.\\u003c/li\\u003e\\n\\u003cli\\u003eTranslate high-level requirements into working prototypes to generate user feedback and discussion.\\u003c/li\\u003e\\n\\u003cli\\u003eAdapt and apply cutting edge computer science to highly technical domains.\\u003c/li\\u003e\\n\\u003cli\\u003eParticipate in code reviews, write test suites, profile components, and take ownership of our software, source code, tools, and processes.\\u003c/li\\u003e\\n\\u003cli\\u003eMentor other developers.\\u003c/li\\u003e\\n\\u003cli\\u003eContribute to a positive, collaborative culture for innovation.\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eRequirements\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003e5+ years of software engineer experience.\\u003c/li\\u003e\\n\\u003cli\\u003eProficiency in frontend technologies (React, Node.js, Webpack).     Experience working with relational and NoSQL databases.\\u003c/li\\u003e\\n\\u003cli\\u003eStrong knowledge of HTTP and REST web services implementation.\\u003c/li\\u003e\\n\\u003cli\\u003eTest driven development and unit testing frameworks.\\u003c/li\\u003e\\n\\u003cli\\u003eModern DevOps experience.\\u003c/li\\u003e\\n\\u003cli\\u003eStandard software engineering tools (git, Jenkins, JIRA).\\u003c/li\\u003e\\n\\u003cli\\u003eThe proven ability to see things as they should be, and to realize that vision.\\u003c/li\\u003e\\n\\u003cli\\u003eComfort with agile development and remote team environments.\\u003c/li\\u003e\\n\\u003cli\\u003eExcellence in verbal and written communication.\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eDesired Skills\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eTypeScript\\u003c/li\\u003e\\n\\u003cli\\u003ePython 3 and web frameworks such as Django or Flask.\\u003c/li\\u003e\\n\\u003cli\\u003eSQL \\u0026amp; relational database modeling.\\u003c/li\\u003e\\n\\u003cli\\u003eExperience in deployment to AWS, especially serverless technologies.\\u003c/li\\u003e\\n\\u003cli\\u003eStrong knowledge of JWT and various web security models.\\u003c/li\\u003e\\n\\u003cli\\u003eD3.js, Vega, Bokeh, Leaflet, and other browser visualization libraries.   Experiment-driven design and iterative features-based development (A/B testing, etc.).\\u003c/li\\u003e\\n\\u003cli\\u003eElasticSearch, Apache Kafka, AWS Kinesis, PostgreSQL, AWS Aurora.\\u003c/li\\u003e\\n\\u003cli\\u003eJava 8 and other JVM-based languages.\\u003c/li\\u003e\\n\\u003cli\\u003ePerformance monitoring and optimization.\\u003c/li\\u003e\\n\\u003cli\\u003eMobile development using Native or Cross-platform (Xamarin, Ionic, React Native).\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\\u003cp\\u003eEducation Required\\u003c/p\\u003e\\n\\u003cul\\u003e\\n\\u003cli\\u003eBS or BA in Computer Science, Mathematics, or Statistics, or equivalent experience.\\u003c/li\\u003e\\n\\u003c/ul\\u003e\\n\",\n" +
                "    \"how_to_apply\": \"\\u003cp\\u003eAPPLY HERE: \\u003ca href=\\\"https://us-strozfriedberg-aon.icims.com/jobs/24315/senior-developer/job\\\"\\u003ehttps://us-strozfriedberg-aon.icims.com/jobs/24315/senior-developer/job\\u003c/a\\u003e\\u003c/p\\u003e\\n\",\n" +
                "    \"company_logo\": null\n" +
                "  }";


        Vector<JobPost> jobLists = new Vector<JobPost>();
        //jobLists.add(new JobPost());
        JobPost jobx = gson.fromJson(tesJson, JobPost.class);

        System.out.println(jobx.toString());




    }
}


