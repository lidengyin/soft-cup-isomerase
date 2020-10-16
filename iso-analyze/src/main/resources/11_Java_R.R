

Arima <- function(url){
    data <- read.csv(url)
    library('forecast') #调出"forecast"包
    library('tseries') #调出"tseries"包
    Sys.setlocale("LC_TIME", "C")

    data.timeseries <- ts(data[1])
    fit <- auto.arima(data.timeseries)
    result <- data.frame(forecast(fit, 100))
    return (result)
    }
