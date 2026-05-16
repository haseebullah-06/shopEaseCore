package extras;

import java.util.ArrayList;

/*
 * ADDED CLASS 2: ReviewSystem
 *
 * Justification:
 * Product reviews bridge the Customer and Product subsystems. After a
 * purchase, customers can rate and comment on products (1-5 stars).
 * Admins can approve reviews before they go public. This adds vertical
 * scope by creating a post-purchase feedback loop and helps future
 * customers make informed decisions — a core feature of any real
 * electronics store like Daraz or Amazon.
 */
public class ReviewSystem {

    // Inner class to represent one review
    public static class Review {
        private String reviewId;
        private String productId;
        private String customerId;
        private int rating;        // 1 to 5
        private String comment;
        private boolean approved;

        public Review(String reviewId, String productId, String customerId,
                      int rating, String comment) {
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating must be 1 to 5.");
            }
            this.reviewId   = reviewId;
            this.productId  = productId;
            this.customerId = customerId;
            this.rating     = rating;
            this.comment    = comment;
            this.approved   = false;
        }

        public void approve()          { this.approved = true; }
        public boolean isApproved()    { return approved; }
        public String getReviewId()    { return reviewId; }
        public String getProductId()   { return productId; }
        public String getCustomerId()  { return customerId; }
        public int getRating()         { return rating; }

        public String toString() {
            String stars = "";
            for (int i = 0; i < rating; i++) stars += "*";
            return "[" + reviewId + "] " + stars + " by " + customerId +
                   "\n  " + comment +
                   "\n  Approved: " + (approved ? "Yes" : "Pending");
        }
    }

    private ArrayList<Review> reviews;
    private int counter;

    public ReviewSystem() {
        reviews = new ArrayList<Review>();
        counter = 1;
    }

    public Review submitReview(String productId, String customerId,
                               int rating, String comment) {
        String id = "REV-" + counter++;
        Review r = new Review(id, productId, customerId, rating, comment);
        reviews.add(r);
        System.out.println("[Review] Submitted by " + customerId +
                           " for " + productId + " (" + rating + "/5). Pending approval.");
        return r;
    }

    // Admin approves a review
    public void approveReview(String reviewId) {
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getReviewId().equals(reviewId)) {
                reviews.get(i).approve();
                System.out.println("[Review] Approved: " + reviewId);
                return;
            }
        }
        System.out.println("[Review] Not found: " + reviewId);
    }

    public double getAverageRating(String productId) {
        int total = 0, count = 0;
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r.getProductId().equals(productId) && r.isApproved()) {
                total += r.getRating();
                count++;
            }
        }
        return (count == 0) ? 0 : (double) total / count;
    }

    public void printProductReviews(String productId) {
        System.out.println("\n=== Reviews for " + productId + " ===");
        System.out.println("Avg Rating: " + getAverageRating(productId) + "/5");
        boolean found = false;
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r.getProductId().equals(productId) && r.isApproved()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No approved reviews yet.");
        System.out.println("==============================\n");
    }
}