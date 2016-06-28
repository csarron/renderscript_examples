## Create the personal library if it doesn't exist. Ignore a warning if the directory already exists.
dir.create(Sys.getenv("R_LIBS_USER"), showWarnings = FALSE, recursive = TRUE)
install.packages(c("data.table", "stringr","cowplot","ggplot2"), Sys.getenv("R_LIBS_USER"), repos = "http://cran.case.edu" ) 
