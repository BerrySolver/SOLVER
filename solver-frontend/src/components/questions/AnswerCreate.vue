<template>
  <div class="answer-editor">
    <div style="width: 900px;">
      <div style="font-size: 17px; text-align: left;">
        <span style="color: #0F4C81; font-size: 22px; font-weight: 700;">{{userNickname}}</span>님, 답변을 남겨주세요!
      </div>
      <div id="answerEditorInsert"></div>
      <div>
        <div class="answer-create-btn" @click="answerCreate">
          남기기
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import API from "@/API.js";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

Vue.use(CKEditor);

class UploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }

  upload() {
    return this.loader.file.then(
      (file) =>
        new Promise((resolve, reject) => {
          this._initRequest();
          this._initListeners(resolve, reject, file);
          this._sendRequest(file);
        })
    );
  }

  _initRequest() {
    const xhr = (this.xhr = new XMLHttpRequest());
    xhr.open("POST", "http://localhost:8080/api/v1/test/image/drag", true);
    xhr.responseType = "json";
  }

  _initListeners(resolve, reject) {
    const xhr = this.xhr;
    const genericErrorText = "파일을 업로드 할 수 없습니다.";

    xhr.addEventListener("error", () => {
      reject(genericErrorText);
    });
    xhr.addEventListener("abort", () => reject());
    xhr.addEventListener("load", () => {
      const response = xhr.response;
      if (!response || response.error) {
        return reject(response && response.error ? response.error.message : genericErrorText);
      }

      resolve({
        default: response.url, //업로드된 파일 주소
      });
    });
  }

  _sendRequest(file) {
    const data = new FormData();
    data.append("upload", file);
    this.xhr.send(data);
  }
}

function MyCustomUploadAdapterPlugin(editor) {
  editor.plugins.get("FileRepository").createUploadAdapter = (loader) => {
    return new UploadAdapter(loader);
  };
}

export default {
  name: 'AnswerCreate',
  props: {
    questionId: String,
  },
  data() {
    return{
      accessToken: "",
      CKEditor: "",
      userNickname: localStorage.getItem("solverNickname")
    }
  },
  methods: {
    answerCreate: function () {
      const content = this.CKEditor.getData()
      console.log(content)
      axios({
        url: API.URL + `answers/${this.questionId}`,
        method: "post",
        data: {
          content: content,
        },
        headers: { Authorization: "Bearer " + this.accessToken },
      })
      .then(() => {
        this.CKEditor.setData('')
      })
      .catch((e) => {
        console.log(e);
      });
    }
  },
  created() {
    this.accessToken = localStorage.getItem('solverToken')
  },
  mounted() {
    ClassicEditor.create(document.querySelector("#answerEditorInsert"), {
      extraPlugins: [MyCustomUploadAdapterPlugin],
        toolbar: {
          items: [
            'heading', '|',
            'bold', 'italic', '|',
            'link', '|',
            'bulletedList', 'numberedList', '|',
            'ckfinder', '|',
            'undo', 'redo'
        ],
      }
    })
      .then((editor) => {
        this.CKEditor = editor;
      })
      .catch((err) => {
        console.error(err.stack);
      });

    let $this = this;
    this.$el.addEventListener("show.bs.modal", function() {
      $this.initUI();
    });
  },
}
</script>

<style>
  .answer-create-btn {
  background-color: #658dc6;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 17px;
  margin: 0px;
  width: 900px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  }

  .answer-create-btn:hover {
    color: white;
    background: #0f4c81;
    transition: 0.4s;
  }

  .answer-editor {
    display: flex;
    margin: 50px 0 100px 0;
    justify-content: center;
  }

  .answer-editor .ck.ck-editor {
    width: 900px;
  }

  .answer-editor .ck.ck-editor__editable {
    height: 400px;
    margin-bottom: 30px;
  }

</style>
