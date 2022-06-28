<template>
  <div id="app">
    <speech-box :headerText="A.header" :footerText="A.footer">
      <div>
        <p>
          {{A.message}}
        </p>
      </div>    
    </speech-box>

    <speech-box class="sanders" :headerText="B.header" :footerText="B.footer">
      <div>
        <p>
          {{B.message}}
        </p>
      </div>    
    </speech-box>
  </div>
</template>

<script>
import SpeechBox from './components/SpeechBox.vue'

export default {
  name: 'App',
  components: {
    SpeechBox
  },
  data: function() {
    return {
      A: {
        header: "A 헤더",
        footer: "A 푸터",
        message: "A 메시지",
      },
      B: {
        header: "B 헤더",
        footer: "B 푸터",
        message: "B 메시지",
      }
    }
  }
}
</script>

<style scoped>
.sanders {
  background-color: antiquewhite;
}
.sanders-content {
  font-family: '굴림';
  text-decoration: underline;
}
</style>
