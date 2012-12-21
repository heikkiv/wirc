package wirc

import com.heikkiv.ml.thomas.*
import com.heikkiv.ml.thomas.mongo.*

class TrainController {

    ClassifierService classifierService

    def index(params) {
        println "Training category $params.category with message $params.message"
        classifierService.train(params.message, params.category)
        render 'OK'
    }

}
