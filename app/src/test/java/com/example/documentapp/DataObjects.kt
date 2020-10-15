package com.example.documentapp

import com.example.documentapp.data.CvData
import com.example.documentapp.data.ExperienceItem

object DataObjects {
    val CV_APPLICANT_JSON = """
{
	"applicant": "Rafal Kowalski",
	"currentRole": "Senior Android Developer",
	"description": "Android developer experienced in RxJava programming in fintech sector",
	"experience": [
      {
       "startDate": "08/2016",
       "endDate": "",
       "company": "Facebook, US",
       "role": "Sr Android Developer",
       "responsibilities": [
              "Designing Custom Views",
              "Grooming Planning tasks",
              "Application development" ]
       }       
      ]
}
"""

    val CV_DATA_MODEL = CvData(
        applicant = "Rafal Kowalski",
        currentRole = "Senior Android Developer",
        description = "Android developer experienced in RxJava programming in fintech sector",
        experience = listOf(
            ExperienceItem(
                startDate = "08/2016",
                endDate = "",
                company = "Facebook, US",
                role = "Sr Android Developer",
                responsibilities = listOf(
                    "Designing Custom Views",
                    "Grooming Planning tasks",
                    "Application development"
                )
            )
        )
    )
}