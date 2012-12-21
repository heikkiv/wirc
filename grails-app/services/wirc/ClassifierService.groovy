package wirc

import com.heikkiv.ml.thomas.*
import com.heikkiv.ml.thomas.mongo.*

class ClassifierService {

    Classifier classifier

    ClassifierService() {
        classifier = new NaiveBayesClassifier()
        classifier.repository = new MongoBayesClassifierRepository()
        classifier.setThreshold('work', 1.5)
    }

    void train(String document, String category) {
       classifier.train(document, category)
    }

    String classify(String document) {
        return classifier.classify(document)
    }

}
