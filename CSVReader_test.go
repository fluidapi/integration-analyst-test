package main

import (
	"os"
	"strconv"
	"strings"
	"testing"
)

func TestAbrirAquivo(t *testing.T) {
	csvFile := AbrirAqruivo("input.csv")

	if csvFile == nil {
		t.Errorf("Não foi possível abrir o arquivo.")
	}
}

func TestLerRegistros(t *testing.T) {
	csvFile, _ := os.Open("input.csv")
	registros := LerRegistros(csvFile)

	if registros == nil {
		t.Errorf("Não foi possível carregar os registros do arquivo.")
	}
}

func TestFormatarData(t *testing.T) {
	dataStr := "2020-01-01"
	dataEsperada := "2020-01"
	dataFormatada := FormatarData(dataStr)

	if dataFormatada != dataEsperada {
		t.Errorf("Data formatada incorreta. Data esperada %v, data formatada %v", dataEsperada, dataFormatada)
	}
}

func TestIncluirRegistro(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01", 2, 1},
		{"2020-02", 5, 2},
	}

	var covid []COVID

	for _, tabela := range registros {

		covid = IncluirRegistro(covid, tabela.mes, strconv.Itoa(tabela.casos), strconv.Itoa(tabela.mortes))
	}

	if len(covid) != 2 {
		t.Errorf("Quantidade de registros diferente.")
	}

	if covid[0].COVIDMonth != registros[0].mes || covid[0].COVIDCases != registros[0].casos || covid[0].COVIDDeaths != registros[0].mortes {
		t.Errorf("Inclsuão de registros diferente.")
	}
}

func TestAtualizarRegistro(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01", 2, 1},
		{"2020-01", 3, 1},
		{"2020-02", 5, 2},
	}

	var covid []COVID

	covid = IncluirRegistro(covid, registros[0].mes, strconv.Itoa(registros[0].casos), strconv.Itoa(registros[0].mortes))
	covid = IncluirRegistro(covid, registros[2].mes, strconv.Itoa(registros[2].casos), strconv.Itoa(registros[2].mortes))

	AtualizarRegistro(covid, strconv.Itoa(registros[1].casos), strconv.Itoa(registros[1].mortes), 0)

	if len(covid) != 2 {
		t.Errorf("Quantidade de registros diferente.")
	}

	if covid[0].COVIDMonth != registros[0].mes || covid[0].COVIDCases != registros[0].casos+registros[1].casos || covid[0].COVIDDeaths != registros[0].mortes+registros[1].mortes {
		t.Errorf("Inclsuão de registros diferente.")
	}
}

func TestProcessarRegistros(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01-30", 2, 1},
		{"2020-01-20", 3, 1},
		{"2020-02-10", 5, 2},
	}

	var data [][]string

	for _, registro := range registros {
		dataStr := registro.mes + "," + strconv.Itoa(registro.casos) + "," + strconv.Itoa(registro.mortes)
		data = append(data, strings.Split(dataStr, ","))
	}

	covid := ProcessarRegistros(data)

	if len(covid) != 2 {
		t.Errorf("Quantidade de registros diferente.")
	}

	if covid[0].COVIDMonth != "2020-01" || covid[0].COVIDCases != registros[0].casos+registros[1].casos || covid[0].COVIDDeaths != registros[0].mortes+registros[1].mortes {
		t.Errorf("Inclsuão de registros diferente.")
	}
}

func TestProcurarIndiceMes(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01-30", 2, 1},
		{"2020-01-20", 3, 1},
		{"2020-02-10", 5, 2},
	}

	var data [][]string

	for _, registro := range registros {
		dataStr := registro.mes + "," + strconv.Itoa(registro.casos) + "," + strconv.Itoa(registro.mortes)
		data = append(data, strings.Split(dataStr, ","))
	}

	covid := ProcessarRegistros(data)

	idx, qtdRegistros := ProcurarIndiceMes(covid, "2020-02")

	if idx != 1 {
		t.Errorf("Posição do mes diferente da esperada.")
	}

	if qtdRegistros != 2 {
		t.Errorf("Quantidade de registros diferente da esperada.")
	}
}

func TestConverterDadosParaJSON(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01-30", 2, 1},
		{"2020-01-20", 3, 1},
		{"2020-02-10", 5, 2},
	}

	var data [][]string

	for _, registro := range registros {
		dataStr := registro.mes + "," + strconv.Itoa(registro.casos) + "," + strconv.Itoa(registro.mortes)
		data = append(data, strings.Split(dataStr, ","))
	}

	covid := ProcessarRegistros(data)

	json_data := ConverterDadosParaJSON(covid)

	if string(json_data) == "null" {
		t.Errorf("Não foi possível convertar os dados para JSON.")
	}
}

func TestGerarArquivoJSON(t *testing.T) {
	registros := []struct {
		mes    string
		casos  int
		mortes int
	}{
		{"2020-01-30", 2, 1},
		{"2020-01-20", 3, 1},
		{"2020-02-10", 5, 2},
	}

	var data [][]string

	for _, registro := range registros {
		dataStr := registro.mes + "," + strconv.Itoa(registro.casos) + "," + strconv.Itoa(registro.mortes)
		data = append(data, strings.Split(dataStr, ","))
	}

	covid := ProcessarRegistros(data)

	json_data := ConverterDadosParaJSON(covid)

	GerarArquivoJSON("output.json", json_data)

	_, err := os.Open("output.json")
	if err != nil {
		t.Errorf("Não foi possível gerar o arquivo JSON.")
	}
}

func TestMain(t *testing.T) {
	main()

	_, err := os.Open("output.json")
	if err != nil {
		t.Errorf("Não foi possível gerar o arquivo JSON.")
	}
}
