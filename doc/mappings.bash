#一、公文基本信息索引构建
# 1、设置mapping
DELETE /search

PUT /search
{
  "mappings":{
    "office":{
      "properties": {
        "ID":{
          "type": "keyword"
        },
        "GUID":{
          "type": "keyword"
        },
        "title":{
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "copy_to": "search"
        },
        "bwtype":{
          "type": "integer"
        },
        "banwenbianhao":{
          "type": "keyword",
          "store": true,
          "copy_to": "search"
        },
        "laiwendanwei":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "createdate":{
          "type": "date",
          "format": "yyyy-MM-dd",
          "store": true
        },
        "createdatetime":{
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis",
          "store": true
        },
        "creatorGUID":{
          "type": "keyword",
          "store": true
        },
        "creatorname":{
          "type": "keyword",
          "store": true
        },
        "dn":{
          "type": "keyword",
          "store": true
        },
        "type":{
          "type": "integer",
          "store": true
        },
        "bureauname":{
          "type": "keyword", 
          "store": true
        },
        "data":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "departments":{
          "type": "nested",
          "properties": {
            "workflowinstanceGUID":{
              "type":"keyword",
              "store":true
            },
            "senduserGUID":{
              "type":"keyword",
              "store":true
            },
            "sendusername":{
              "type":"keyword",
              "store":true
            },
            "departmentname":{
              "type":"keyword",
              "store":true
            },
            "departmentGUID":{
              "type":"keyword",
              "store":true
            },
           "dn":{
              "type":"keyword",
              "store":true
            }
          }
        }
      }
    },
    "document":{
      "properties": {
        "ID":{
          "type": "keyword"
        },
        "GUID":{
          "type": "keyword"
        },
        "title":{
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "copy_to": "search"
        },
        "bwtype":{
          "type": "integer"
        },
        "banwenbianhao":{
          "type": "keyword",
          "store": true,
          "copy_to": "search"
        },
        "laiwendanwei":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "createdate":{
          "type": "date",
          "format": "yyyy-MM-dd",
          "store": true
        },
        "createdatetime":{
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis",
          "store": true
        },
        "creatorGUID":{
          "type": "keyword",
          "store": true
        },
        "creatorname":{
          "type": "keyword",
          "store": true
        },
        "dn":{
          "type": "keyword",
          "store": true
        },
        "type":{
          "type": "integer",
          "store": true
        },
        "bureauname":{
          "type": "keyword", 
          "store": true
        },
        "data":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "departments":{
          "type": "nested",
          "properties": {
            "workflowinstanceGUID":{
              "type":"keyword",
              "store":true
            },
            "senduserGUID":{
              "type":"keyword",
              "store":true
            },
            "sendusername":{
              "type":"keyword",
              "store":true
            },
            "departmentname":{
              "type":"keyword",
              "store":true
            },
            "departmentGUID":{
              "type":"keyword",
              "store":true
            },
           "dn":{
              "type":"keyword",
              "store":true
            }
          }
        }
      }
    },
    "comment":{
      "properties": {
        "ID":{
          "type": "keyword"
        },
        "GUID":{
          "type": "keyword"
        },
        "title":{
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "copy_to": "search"
        },
        "bwtype":{
          "type": "integer"
        },
        "banwenbianhao":{
          "type": "keyword",
          "store": true,
          "copy_to": "search"
        },
        "laiwendanwei":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "createdate":{
          "type": "date",
          "format": "yyyy-MM-dd",
          "store": true
        },
        "createdatetime":{
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis",
          "store": true
        },
        "creatorGUID":{
          "type": "keyword",
          "store": true
        },
        "creatorname":{
          "type": "keyword",
          "store": true
        },
        "dn":{
          "type": "keyword",
          "store": true
        },
        "type":{
          "type": "integer",
          "store": true
        },
        "bureauname":{
          "type": "keyword", 
          "store": true
        },
        "data":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "departments":{
          "type": "nested",
          "properties": {
            "workflowinstanceGUID":{
              "type":"keyword",
              "store":true
            },
            "senduserGUID":{
              "type":"keyword",
              "store":true
            },
            "sendusername":{
              "type":"keyword",
              "store":true
            },
            "departmentname":{
              "type":"keyword",
              "store":true
            },
            "departmentGUID":{
              "type":"keyword",
              "store":true
            },
           "dn":{
              "type":"keyword",
              "store":true
            }
          }
        }
      }
    },
    "attachment":{
      "properties": {
        "ID":{
          "type": "keyword"
        },
        "GUID":{
          "type": "keyword"
        },
        "title":{
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "copy_to": "search"
        },
        "bwtype":{
          "type": "integer"
        },
        "banwenbianhao":{
          "type": "keyword",
          "store": true,
          "copy_to": "search"
        },
        "laiwendanwei":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "createdate":{
          "type": "date",
          "format": "yyyy-MM-dd",
          "store": true
        },
        "createdatetime":{
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis",
          "store": true
        },
        "creatorGUID":{
          "type": "keyword",
          "store": true
        },
        "creatorname":{
          "type": "keyword",
          "store": true
        },
        "dn":{
          "type": "keyword",
          "store": true
        },
        "type":{
          "type": "integer",
          "store": true
        },
        "bureauname":{
          "type": "keyword", 
          "store": true
        },
        "data":{
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "store": true,
          "copy_to": "search"
        },
        "departments":{
          "type": "nested",
          "properties": {
            "workflowinstanceGUID":{
              "type":"keyword",
              "store":true
            },
            "senduserGUID":{
              "type":"keyword",
              "store":true
            },
            "sendusername":{
              "type":"keyword",
              "store":true
            },
            "departmentname":{
              "type":"keyword",
              "store":true
            },
            "departmentGUID":{
              "type":"keyword",
              "store":true
            },
           "dn":{
              "type":"keyword",
              "store":true
            }
          }
        }
      }
    }
  },
  "settings":{
      "index.number_of_shards":3,
      "index.number_of_replicas":0
    }
}