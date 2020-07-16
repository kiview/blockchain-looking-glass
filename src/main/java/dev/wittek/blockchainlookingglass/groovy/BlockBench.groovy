package dev.wittek.blockchainlookingglass.groovy

import org.hipparchus.stat.descriptive.DescriptiveStatistics

// Let's run a PoW benchmark!

def runBenchmark(int i, String difficulty) {
    def proxy = ProxyMetaClass.getInstance(GroovyChain.class)
    proxy.interceptor = new BenchmarkInterceptor()
    proxy.use {
        def c = new GroovyChain("foo", difficulty)

        i.times {
            c << "$it"
        }
    }
    println proxy.interceptor.statistic()
    return proxy.interceptor.getCalls()
}

def calculateStatistics(Map calls, String method) {
    List methodCalls = calls[method] as List

    def statistics = new DescriptiveStatistics(methodCalls.size() / 2)
    0.step methodCalls.size(), 2, {
        statistics.addValue(methodCalls[it + 1] - methodCalls[it])
    }

    return statistics
}

def runAndPrintBenchmark(int i, String difficulty) {
    def b = runBenchmark(i, difficulty)
    def s = calculateStatistics(b, "addAndMineBlock")
    println s
}

runAndPrintBenchmark(10, "0")
runAndPrintBenchmark(10, "00")
runAndPrintBenchmark(10, "000")
runAndPrintBenchmark(10, "0000")
runAndPrintBenchmark(10, "00000")