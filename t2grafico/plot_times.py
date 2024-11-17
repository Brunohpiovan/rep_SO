import pandas as pd
import matplotlib.pyplot as plt

# Ler os dados do CSV
sequential_times = pd.read_csv('sequential_times.csv')
parallel_times = pd.read_csv('parallel_times.csv')
tbb = pd.read_csv('tbb.csv')

# Criar gráfico para tempos sequenciais
plt.plot(sequential_times['Execução'], sequential_times['Tempo'], label='Sequencial')

# Criar gráfico para tempos paralelos
plt.plot(parallel_times['Execução'], parallel_times['Tempo'], label='Paralelo')

# Criar gráfico para tempos paralelos tbb
plt.plot(tbb['Execução'], tbb['Tempo'], label='Tbb')

# Configurações do gráfico
plt.xlabel('Execução')
plt.ylabel('Tempo (ms)')
plt.title('Tempos de Execução nas 30 Execuções')
plt.legend()

# Exibir gráfico
plt.show()
