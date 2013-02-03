package wirc

import com.heikkiv.ml.thomas.*
import com.heikkiv.ml.thomas.mongo.*

import javax.annotation.PostConstruct

class ClassifierService {

    def grailsApplication
    Classifier classifier

    ClassifierService() {
        classifier = new NaiveBayesClassifier()
    }

    @PostConstruct
    void setThreshold() {
        classifier.setThreshold('work', grailsApplication.config.thomas.threshold as double)
    }

    void train(String document, String category) {
       classifier.train(document, category)
    }

    String classify(String document) {
        return classifier.classify(document)
    }

}
