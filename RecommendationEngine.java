import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationEngine {
    public static void main(String[] args) throws IOException, TasteException {
        // Load dataset
        DataModel model = new FileDataModel(new File("data.csv"));

        // Compute similarity between users
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Define neighborhood (2 nearest neighbors)
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

        // Build recommender
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Recommend items for user with ID = 1
        List<RecommendedItem> recommendations = recommender.recommend(1, 3);

        System.out.println("=== Recommended Items for User 1 ===");
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Item ID: " + recommendation.getItemID() +
                               " | Predicted Rating: " + recommendation.getValue());
        }
    }
}
recommendationengine
