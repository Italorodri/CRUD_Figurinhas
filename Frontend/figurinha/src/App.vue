<template>
  <!--<MenuTopo />-->
  <v-data-table :headers="headers" :items="desserts">
    <template v-slot:top>
      <v-toolbar flat color="#a8d8ff" dark>
        <v-toolbar-title>Lista de Figurinhas</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="800px">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props"> Adicionar Figurinha </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field
                      v-model="editedItem.nome"
                      label="Nome"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field
                      v-model="editedItem.descricao"
                      label="Descrição"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field
                      v-model="editedItem.anoLancamento"
                      label="Ano de Lançamento"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field
                      v-model="editedItem.categoria"
                      label="Categoria"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="red" variant="text" @click="close">
                Cancelar
              </v-btn>
              <v-btn color="blue-darken-1" variant="text" @click="save">
                Cadastrar
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Tem certeza que deseja deletar esse item?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="closeDelete"
                >Cancelar</v-btn
              >
              <v-btn color="red" variant="text" @click="deleteItemConfirm"
                >Deletar</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon class="me-2" size="small" @click="editItem(item)" color="blue">
        mdi-pencil
      </v-icon>
      <v-icon size="small" @click="deleteItem(item)" color="red">
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
</template>

<script>
// Importando o Axios para enviar requisições HTTP (CRUD) permitindo que a aplicação interaja com o servidor
import axios from "axios";
//import MenuTopo from "components/MenuTopo.vue"; // Importando o componente do menu

export default {
  //components: {
  //MenuTopo, // Registra o componente do menu
  //},

  //A função data é usada para declarar as variáveis reativas do componente
  data: () => ({
    dialog: false, //Modal
    dialogDelete: false, //Modal
    headers: [
      //Array de objetos
      { text: "Nome", value: "nome" },
      { text: "Descrição", value: "descricao" },
      { text: "Ano de Lançamento", value: "anoLancamento" },
      { text: "Categoria", value: "categoria" },
      { text: "Ações", value: "actions", sortable: false },
    ],
    desserts: [], //Armazena a lista de figurinhas
    editedIndex: -1, //-1 indica que nenhum item está sendo editado no momento. Este índice é útil para saber qual item do array desserts está sendo modificado.
    editedItem: {
      //É um objeto que armazena os valores dos campos do item atualmente sendo editado.
      id: "",
      nome: "",
      descricao: "",
      anoLancamento: "",
      categoria: "",
    },
    defaultItem: {
      //serve como um modelo para redefinir o editedItem após uma edição ser concluída ou cancelada.
      id: "",
      nome: "",
      descricao: "",
      anoLancamento: "",
      categoria: "",
    },
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Nova figurinha" : "Editar figurinha";
    },
  },

  // watchers são úteis para executar ações de limpeza ou fechar diálogos/modais com base na mudança de estado dessas propriedades.
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      axios
        .get("http://localhost:8080/figurinhas")
        .then((response) => {
          this.desserts = response.data;
        })
        .catch((error) => {
          console.error("Erro ao buscar figurinhas:", error);
        });
    },

    editItem(item) {
      this.editedIndex = this.desserts.findIndex((d) => d.id === item.id); // findIndex é um método de array JavaScript que retorna o índice do primeiro elemento que satisfaz a condição fornecida
      this.editedItem = Object.assign({}, item); //Object.assign({}, item) cria uma cópia superficial do objeto item fornecido. evitar mutação
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.desserts.findIndex((d) => d.id === item.id);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      axios
        .delete(`http://localhost:8080/figurinhas/${this.editedItem.id}`)
        .then(() => {
          this.closeDelete();
          this.initialize(); // Atualizar a lista
        })
        .catch((error) => {
          console.error("Erro ao deletar figurinha:", error);
        });
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    //Atualizações e Criação
    save() {
      const method = this.editedIndex > -1 ? "put" : "post";
      const url =
        this.editedIndex > -1
          ? `http://localhost:8080/figurinhas/${this.editedItem.id}`
          : "http://localhost:8080/figurinhas";
      //Este código faz uma requisição HTTP usando Axios para salvar ou atualizar um item ("figurinha") em um servidor.
      //Se a requisição for bem-sucedida, ele fecha o diálogo de edição e atualiza a lista de itens através do método initialize.
      //Em caso de erro na requisição, ele registra o erro no console.
      axios({ method, url, data: this.editedItem })
        .then(() => {
          this.close();
          this.initialize(); // Atualizar a lista
        })
        .catch((error) => {
          console.error("Erro ao salvar figurinha:", error);
        });
    },
  },
};
</script>

<style></style>
