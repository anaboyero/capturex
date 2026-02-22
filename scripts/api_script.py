import requests

url = "http://localhost:8080/learning-artifacts"


correctLA = {
  "description": "Basic controller that registers a learning artifact from a developer",
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": "https://my-personal-github.com"
}

nullDescriptionLA = {
  "description": None,
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": "https://my-personal-github.com"
}

nullLessonLearnedLA = {
  "description":"Basic controller that registers a learning artifact from a developer",
  "lessonLearned": None,
  "projectUrl": "https://my-personal-github.com"
}

nullProjectUrlLA = {
  "description":"Basic controller that registers a learning artifact from a developer",
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": None
}

shortDescriptionLA = {
  "description": "Basic controller",
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": "https://my-personal-github.com"
}

blankAndLongDescriptionLA = {
  "description": "                       Basic controller                          ",
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": "https://my-personal-github.com"
}

blankLessonLearnedLA = {
  "description": "Basic controller that registers a learning artifact from a developer",
  "lessonLearned": "                       ",
  "projectUrl": "https://my-personal-github.com"
}   

wrongProjectUrlLA = {
  "description": "Basic controller that registers a learning artifact from a developer",
  "lessonLearned": "How to work with an agent throught TDD to create a very simple vertical slice",
  "projectUrl": "my-personal-github.com"
}

request_data = [
    ("correctLA", correctLA),
    ("nullDescriptionLA", nullDescriptionLA),
    ("nullLessonLearnedLA", nullLessonLearnedLA),
    ("nullProjectUrlLA", nullProjectUrlLA),
    ("shortDescriptionLA", shortDescriptionLA),
    ("blankAndLongDescriptionLA", blankAndLongDescriptionLA),
    ("blankLessonLearnedLA", blankLessonLearnedLA),
    ("wrongProjectUrlLA", wrongProjectUrlLA),
]

for name, data in request_data:
    print("\n*** Sending: " + name)
    response = requests.post(url, json=data)
    print(response.status_code)
    print(response.json())