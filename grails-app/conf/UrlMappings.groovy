class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
        "/api/$controller"(parseRequest: true){
			action = [GET: "list"]
		}
         "/api/$controller/$id"(parseRequest: true){
			action = [GET: "show"]
             constraints {
                 // apply constraints here
                 //id matches:/\d+
             }
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
