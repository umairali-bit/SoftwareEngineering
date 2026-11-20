package day2;

public class SpringBoot {

    /*
    What is the difference between @Controller and @RestController in SpringBoot? Which one would you use when building
    a REST API for a banking application, and Why?

    In SpringBoot @Controller is used to return web pages (HTML views) while @RestController return
    raw data like JSON and XML

    @RestController is actually a specialized version of @Controller that automatically combines @Controller
    + @ResponseBody

    For a banking REST API, I would choose @RestController because endpoints such as account balances,
    transactions, and customer information need to return structured JSON responses to clients.



     */


    /*
    What is the difference between supervised learning and unsupervised learning?

    Give one practical example of each in a banking or enterprise application.

    Answer as you would in an AI Engineer interview.

    Supervised learning uses algorithms that learns for the data, example regression, classification
    Unsupervised learning uses algorithms that basically create their own path and learn from rewards,
    reinforcement

    in a banking application, if I want to guess how many customers left on the basis of this new policy
    Id use classification which is a branch of supervised learning

    In an enterprise application for example a grocery store, if I want to see how many people bought eggs and bread
    together Id use association rule mining which is a branch of unsupervised learning



    LLM Answer "Supervised learning trains models using labeled data, where the expected outcomes are known.
                It includes classification and regression. For example, in banking, I could use
                classification to predict whether a customer is likely to leave after a policy change.

                Unsupervised learning analyzes unlabeled data to discover hidden patterns or relationships.
                For example, a grocery store could use association rule mining to identify products frequently
                purchased together, such as bread and eggs.

                Reinforcement learning is different because an agent learns through interactions with an environment and
                receives rewards or penalties for its actions."
     */


}
