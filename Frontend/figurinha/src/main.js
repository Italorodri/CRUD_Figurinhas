import "./assets/main.css";

import { createApp } from "vue";
import App from "./App.vue";
//import Vuetify from 'vuetify/lib';

// Vuetify
import "@mdi/font/css/materialdesignicons.css";
import "@mdi/font/css/materialdesignicons.min.css";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "vuetify/styles";

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: "mdi",
  },
});

createApp(App).use(vuetify).mount("#app");
