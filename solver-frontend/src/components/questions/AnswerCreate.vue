<template>
  <div class="answer-create">
		<div id="">
      <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
    </div>
	</div>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
    name: 'AnswerCreate',
		data() {
            return {
                editor: ClassicEditor,
                editorData: '<p>Content of the editor.</p>',
                editorConfig: {
                    // The configuration of the editor.
                }
            };
        }
}
</script>

<style>

</style>